package com.somuncu.footballmarkt.service.playervalueestimationgame;

import com.somuncu.footballmarkt.entities.PlayerValueEstimationGame;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.response.PlayPlayerValueEstimationGameResponse;
import com.somuncu.footballmarkt.dtos.playervalueestimationgame.PlayerValueEstimationGameDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface PlayerValueEstimationGameService {

    public PlayerValueEstimationGame createPlayerValueEstimationGame(UserDetails userDetails);
    public PlayPlayerValueEstimationGameResponse playPlayerValueEstimationGame(Long gameId , Long playerId , UserDetails userDetails);
    public void deleteById(Long id);
    public PlayerValueEstimationGameDto convert(PlayerValueEstimationGame playerValueEstimationGame);
    public User getCurrentUser(UserDetails userDetails);
}
