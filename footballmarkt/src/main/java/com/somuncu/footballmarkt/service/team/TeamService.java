package com.somuncu.footballmarkt.service.team;

import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.team.CreateTeamRequest;
import com.somuncu.footballmarkt.request.team.UpdateTeamRequest;
import com.somuncu.footballmarkt.response.dtos.team.TeamDto;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.parsers.SAXParser;
import java.util.List;

public interface TeamService {

    public TeamDto getTeamById(Long id);
    public TeamDto getTeamByTeamName(String teamName);
    public List<TeamDto> getTeamsByUserName(String userName);
    public TeamDto createTeam(CreateTeamRequest createTeamRequest , UserDetails userDetails);
    public TeamDto updateTeam(UpdateTeamRequest updateTeamRequest , UserDetails userDetails);
    public String getTeamNameByTeamId(Long id);
    public String getUserNameByTeamId(Long id);
    public void deleteTeam(Long id , UserDetails userDetails);
    public User getCurentUser(UserDetails userDetails);

}
