package com.somuncu.footballmarkt.service.clubvalueestimationgame;

import com.somuncu.footballmarkt.core.utiliites.exceptions.clubvalueestimationgame.NoClubValueEstimationGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.dao.ClubValueEstimationGameRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.ClubValueEstimationGame;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.response.PlayClubValueEstimationGameResponse;
import com.somuncu.footballmarkt.dtos.clubvalueestimationgame.ClubValueEstimationGameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubValueEstimationGameServiceImpl implements ClubValueEstimationGameService {

    private final ClubValueEstimationGameRepository clubValueEstimationGameRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final ModelMapperService modelMapperService;

    @Transactional
    @Override // clubları set et , correct clubı set et , userını set et , // controllerda dtosnunu döndürmem lazım
    public ClubValueEstimationGame createClubValueEstimationGame(UserDetails userDetails) {

        Club clubOne;
        Club clubTwo;
        Double clubValue1;
        Double clubValue2;
        Double absoluteDifference;


        do {

            clubOne = clubRepository.findRandomClub();
            clubTwo = clubRepository.findRandomClub();

            clubValue1 = clubOne.getClubValue();
            clubValue2 = clubTwo.getClubValue();

            absoluteDifference = Math.abs(clubValue2 - clubValue1);

        }
        while(clubOne.getId().equals(clubTwo.getId()) || clubOne.getClubValue().equals(clubTwo.getClubValue()) || absoluteDifference > 100000000);

        ClubValueEstimationGame clubValueEstimationGame = new ClubValueEstimationGame();
        clubValueEstimationGame.setClubOne(clubOne);
        clubValueEstimationGame.setClubTwo(clubTwo);
        User user = this.getCurrentUser(userDetails);
        clubValueEstimationGame.setUser(user);
        if(clubOne.getClubValue() > clubTwo.getClubValue()) {
            clubValueEstimationGame.setCorrectClub(clubOne);
        }
        else{
            clubValueEstimationGame.setCorrectClub(clubTwo);
        }

        this.clubValueEstimationGameRepository.save(clubValueEstimationGame);

        return clubValueEstimationGame;
    }

    @Transactional
    @Override   // choosenı set et ancak transaction yönetimiyle yap (listeden güncelleme)
    public PlayClubValueEstimationGameResponse playClubValueEstimationGame(Long gameId, Long clubId, UserDetails userDetails) {

        ClubValueEstimationGame clubValueEstimationGame = this.clubValueEstimationGameRepository.findById(gameId).orElseThrow(()-> new NoClubValueEstimationGameFoundException("No club value estimation game found to play"));
        User user = this.getCurrentUser(userDetails);
        if(!clubValueEstimationGame.getUser().equals(user)) {
            throw new NotAbleToDoThisOperationException("You can not pick player for this game !");
        }
        if(!clubValueEstimationGame.getClubOne().getId().equals(clubId) && !clubValueEstimationGame.getClubTwo().getId().equals(clubId)) {
            throw new NotAbleToDoThisOperationException("Do not enter invalid clubId");
        }

        Club choosenClub = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubValueEstimationGameFoundException("No club found to pick"));

        ClubValueEstimationGame gameToPlay = user.getClubValueEstimationGames().stream().filter(game -> game.getId().equals(clubValueEstimationGame.getId())).findFirst().orElseThrow(()-> new NoClubValueEstimationGameFoundException("No club value estimation game found for current user"));
        gameToPlay.setChosenClub(choosenClub);
        user.updateClubValueEstimationSuccess();
        userRepository.save(user);

        return this.modelMapperService.forResponse().map(clubValueEstimationGame , PlayClubValueEstimationGameResponse.class);
    }

    @Transactional
    @Override
    public void deleteClubValueEstimationGameById(Long id) {

        ClubValueEstimationGame clubValueEstimationGame = this.clubValueEstimationGameRepository.findById(id).orElseThrow(()-> new NoClubValueEstimationGameFoundException("No club value estimation game found to delete"));
        User gameUser = clubValueEstimationGame.getUser();
        gameUser.getClubValueEstimationGames().remove(clubValueEstimationGame);
        gameUser.updateClubValueEstimationSuccess();
        this.userRepository.save(gameUser);

    }

    @Override
    public User getCurrentUser(UserDetails userDetails) {
        String userMail = userDetails.getUsername();
        return this.userRepository.findUserByEmail(userMail).orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    @Override
    public ClubValueEstimationGameDto convertToDto(ClubValueEstimationGame clubValueEstimationGame) {
        return this.modelMapperService.forResponse().map(clubValueEstimationGame , ClubValueEstimationGameDto.class);
    }
}
