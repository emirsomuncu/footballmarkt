package com.somuncu.footballmarkt.service.clubvalueestimationgame;

import com.somuncu.footballmarkt.entities.ClubValueEstimationGame;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.response.PlayClubValueEstimationGameResponse;
import com.somuncu.footballmarkt.dtos.clubvalueestimationgame.ClubValueEstimationGameDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClubValueEstimationGameService {


    public ClubValueEstimationGame createClubValueEstimationGame(UserDetails userDetails);
    public PlayClubValueEstimationGameResponse playClubValueEstimationGame(Long gameId , Long clubId , UserDetails userDetails);
    public void deleteClubValueEstimationGameById(Long id);
    public User getCurrentUser(UserDetails userDetails);
    public ClubValueEstimationGameDto convertToDto(ClubValueEstimationGame clubValueEstimationGame);

}
