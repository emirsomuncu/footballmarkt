package com.somuncu.footballmarkt.service.team;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.team.NoTeamFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.dao.TeamRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.Team;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.team.CreateTeamRequest;
import com.somuncu.footballmarkt.request.team.UpdateTeamRequest;
import com.somuncu.footballmarkt.response.dtos.team.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;

    @Cacheable(value = "getTeamById" , key = "#id")
    @Override
    public TeamDto getTeamById(Long id) {

        Team team = this.teamRepository.findById(id).orElseThrow(()-> new NoTeamFoundException("No team found"));
        return this.modelMapperService.forResponse().map(team , TeamDto.class);
    }

    @Cacheable(value = "getTeamByTeamName" , key = "#teamName")
    @Override
    public TeamDto getTeamByTeamName(String teamName) {

        Team team = this.teamRepository.findTeamByName(teamName).orElseThrow(()-> new NoTeamFoundException("No team found with this name : " + teamName ));
        return this.modelMapperService.forResponse().map(team , TeamDto.class);
    }

    @Cacheable(value = "getTeamsByUserName" , key = "#userName")
    @Override
    public List<TeamDto> getTeamsByUserName(String userName) {

       List<Team> teams = this.teamRepository.findAllByUserName(userName);
       return teams.stream().map(team -> this.modelMapperService.forResponse().map(team , TeamDto.class)).collect(Collectors.toList());
    }

    @Caching(put = {
            @CachePut(value = "getTeamById" , key = "#result.id") ,
            @CachePut(value = "getTeamByTeamName" , key = "#result.name")
            } ,
            evict = {
            @CacheEvict(value= "getTeamsByUserName" , key= "#result.userName")
            }
    )
    @Override
    public TeamDto createTeam(CreateTeamRequest createTeamRequest, UserDetails userDetails) {

        Team team = new Team();
        team.setName(createTeamRequest.getName());
        team.setFormation(createTeamRequest.getFormation());

        List<Long> idsList = createTeamRequest.getPlayersIdsList();
        List<Player> playersForTeam = new ArrayList<>();
        for(Long id : idsList) {
          Player player = this.playerRepository.findById(id).orElseThrow(()-> new NoPlayerFoundException("No player found"));
          playersForTeam.add(player);
        }
        team.setPlayers(playersForTeam);

        Double totalMarketValue = 0.0;
        for(Player player :playersForTeam) {
            Double marketValue = player.getMarketValue();
            totalMarketValue = totalMarketValue + marketValue;
        }
        team.setTotalMarketValue(totalMarketValue);

        team.setUser(this.getCurentUser(userDetails));
        Team savedTeam = this.teamRepository.save(team);
        return this.modelMapperService.forResponse().map(savedTeam , TeamDto.class);
    }

    @Caching(put = {
            @CachePut(value = "getTeamById" , key = "#result.id"),
            @CachePut(value = "getTeamByTeamName" , key = "#result.name")
            },
            evict = {
            @CacheEvict(value= "getTeamsByUserName" , key= "#result.userName")
            }
    )
    @Override
    public TeamDto updateTeam(UpdateTeamRequest updateTeamRequest , UserDetails userDetails) {

       Team team = this.teamRepository.findById(updateTeamRequest.getId()).orElseThrow(()-> new NoTeamFoundException("No team found to update"));

       team.setName(updateTeamRequest.getName());
       team.setFormation(updateTeamRequest.getFormation());

        List<Long> idsList = updateTeamRequest.getPlayersIdsList();
        List<Player> playersForTeam = new ArrayList<>();
        for(Long id : idsList) {
            Player player = this.playerRepository.findById(id).orElseThrow(()-> new NoPlayerFoundException("No player found"));
            playersForTeam.add(player);
        }
        team.setPlayers(playersForTeam);

        Double totalMarketValue = 0.0;
        for(Player player :playersForTeam) {
            Double marketValue = player.getMarketValue();
            totalMarketValue = totalMarketValue + marketValue;
        }
        team.setTotalMarketValue(totalMarketValue);

        Team updatedTeam = this.teamRepository.save(team);
        return this.modelMapperService.forResponse().map(updatedTeam , TeamDto.class);
    }

    @Override
    public String getTeamNameByTeamId(Long id) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new NoTeamFoundException("No team found to get team name"));
        return team.getName();
    }

    @Override
    public String getUserNameByTeamId(Long id) {
        Team team = this.teamRepository.findById(id).orElseThrow(() -> new NoTeamFoundException("No team found to get team name"));
        return team.getUser().getName();
    }

    @Caching(evict = {
            @CacheEvict(value = "getTeamById" , key = "#id"),
            @CacheEvict(value = "getTeamByTeamName" , key = "#root.target.getTeamNameByTeamId(#id)" , beforeInvocation = true),
            @CacheEvict(value = "getTeamsByUserName" , key= "#root.target.getUserNameByTeamId(#id)" , beforeInvocation = true)
            }
    )
    @Override
    public void deleteTeam(Long id , UserDetails userDetails) {

        Team team = this.teamRepository.findById(id).orElseThrow(()-> new NoTeamFoundException("No team found to delete"));
        User user = this.getCurentUser(userDetails);
        if(!team.getUser().equals(user)) {
            throw new NotAbleToDoThisOperationException("You can only delete your teams");
        }
        this.teamRepository.delete(team);
    }

    @Override
    public User getCurentUser(UserDetails userDetails) {

        String userMail = userDetails.getUsername();
        return this.userRepository.findUserByEmail(userMail).orElseThrow(()-> new UserNotFoundException("You should login to do this operation"));
    }
}
