package com.somuncu.footballmarkt.service.playervalueestimationgame;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.playervalueestimationgame.NoPlayerValueEstimationGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.dao.PlayerValueEstimationGameRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.PlayerValueEstimationGame;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.response.PlayPlayerValueEstimationGameResponse;
import com.somuncu.footballmarkt.response.dtos.playervalueestimationgame.PlayerValueEstimationGameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerValueEstimationGameServiceImpl implements PlayerValueEstimationGameService{

    private final PlayerValueEstimationGameRepository playerValueEstimationGameRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;

    @Transactional
    @Override
    public PlayerValueEstimationGame createPlayerValueEstimationGame(UserDetails userDetails) {

        Player playerOne;
        Player playerTwo;
        Double marketValue1;
        Double marketValue2;
        Double absoluteDifference;

        do {
            playerOne = playerRepository.findRandomPlayer();
            playerTwo = playerRepository.findRandomPlayer();

            marketValue1 = playerOne.getMarketValue();
            marketValue2 = playerTwo.getMarketValue();

            absoluteDifference = Math.abs(marketValue2 - marketValue1);

        } while (playerOne.getId().equals(playerTwo.getId()) || playerOne.getMarketValue().equals(playerTwo.getMarketValue()) || absoluteDifference >= 50000000);

        PlayerValueEstimationGame playerValueEstimationGame  = new PlayerValueEstimationGame();
        playerValueEstimationGame.setPlayerOne(playerOne);
        playerValueEstimationGame.setPlayerTwo(playerTwo);
        if(playerTwo.getMarketValue() > playerOne.getMarketValue()) {
            playerValueEstimationGame.setCorrectPlayer(playerTwo);
        }else {
            playerValueEstimationGame.setCorrectPlayer(playerOne);
        }

        User user = this.getCurrentUser(userDetails);
        playerValueEstimationGame.setUser(user);

        this.playerValueEstimationGameRepository.save(playerValueEstimationGame);

        return playerValueEstimationGame;
    }

    @Transactional
    @Override
    public PlayPlayerValueEstimationGameResponse playPlayerValueEstimationGame(Long gameId, Long playerId, UserDetails userDetails) {

        Player choosenPlayer = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to pick"));
        PlayerValueEstimationGame playerValueEstimationGame= this.playerValueEstimationGameRepository.findById(gameId).orElseThrow(()-> new NoPlayerValueEstimationGameFoundException("No game found to choose player"));

        if(choosenPlayer != playerValueEstimationGame.getPlayerOne() && choosenPlayer != playerValueEstimationGame.getPlayerTwo()) {
            throw new NotAbleToDoThisOperationException("Do not enter invalid playerId");
        }

        User currentUser = this.getCurrentUser(userDetails);
        if(!currentUser.equals(playerValueEstimationGame.getUser())) {
            throw new NotAbleToDoThisOperationException("You can not pick player for this game !");
        }

        PlayerValueEstimationGame gameToPlay = currentUser.getPlayerValueEstimationGames().stream().filter(game -> game.getId().equals(playerValueEstimationGame.getId())).findFirst().orElseThrow(()-> new NoPlayerValueEstimationGameFoundException("No game found to play"));
        gameToPlay.setChosenPlayer(choosenPlayer);
        currentUser.updatePlayerValueEstimationSuccess();
        this.userRepository.save(currentUser);

        return this.modelMapperService.forResponse().map(playerValueEstimationGame , PlayPlayerValueEstimationGameResponse.class);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        PlayerValueEstimationGame game = this.playerValueEstimationGameRepository.findById(id).orElseThrow(()-> new NoPlayerValueEstimationGameFoundException("No player value estimation game found to delete"));
        User gameUser = game.getUser();
        gameUser.getPlayerValueEstimationGames().remove(game);
        gameUser.updatePlayerValueEstimationSuccess();
        this.userRepository.save(gameUser);

    }

    @Override
    public PlayerValueEstimationGameDto convert(PlayerValueEstimationGame playerValueEstimationGame) {

        PlayerValueEstimationGameDto playerValueEstimationGameDto = this.modelMapperService.forResponse().map(playerValueEstimationGame , PlayerValueEstimationGameDto.class);
        return playerValueEstimationGameDto;
    }

    @Override
    public User getCurrentUser(UserDetails userDetails) {

        String userMail = userDetails.getUsername();
        User user = this.userRepository.findUserByEmail(userMail).orElseThrow(()-> new UserNotFoundException("User not found"));

        return user;
    }
}
