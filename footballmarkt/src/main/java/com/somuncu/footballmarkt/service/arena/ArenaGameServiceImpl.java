package com.somuncu.footballmarkt.service.arena;

import com.somuncu.footballmarkt.core.utiliites.exceptions.arenagame.InvalidPlayerIdException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.arenagame.NoArenaGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ArenaGameRepository;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.entities.ArenaGame;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.response.PlayArenaGameResponse;
import com.somuncu.footballmarkt.dtos.arenagame.ArenaGameDto;
import com.somuncu.footballmarkt.service.rules.ArenaGameServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArenaGameServiceImpl implements ArenaGameService{

    private final ArenaGameRepository arenaGameRepository;
    private final PlayerRepository playerRepository;
    private final ArenaGameServiceImplRules arenaGameServiceImplRules;
    private final ModelMapperService modelMapperService;


    @Transactional
    @Override
    public ArenaGame createArenaGame(Long clubId , Long leagueId) {

        if(leagueId != null && clubId == null) {

            Player firstPlayer = this.playerRepository.findOnePlayerByUsingLeagueId(leagueId);
            Player secondPlayer = this.playerRepository.findOneRandomPlayerByLeagueIdNativeExcludingPlayer(leagueId , firstPlayer.getId());

            ArenaGame arenaGame = new ArenaGame();
            arenaGame.setPlayer1(firstPlayer);
            arenaGame.setPlayer2(secondPlayer);
            arenaGame.setWinnerPlayer(null);
            this.arenaGameServiceImplRules.checkIfArenaGamePlayerIsNull(firstPlayer , secondPlayer);

            ArenaGame savedArenaGame = arenaGameRepository.save(arenaGame);

            firstPlayer.getArenaGamesAsPlayer1().add(savedArenaGame);
            firstPlayer.updateArenaPlayed();
            playerRepository.save(firstPlayer);

            secondPlayer.getArenaGamesAsPlayer2().add(savedArenaGame);
            secondPlayer.updateArenaPlayed();
            playerRepository.save(secondPlayer);

            return arenaGame;
        }
        else if (clubId != null) {

            Player firstPLayer = this.playerRepository.findOnePlayerByClubId(clubId);
            Player secondPlayer = this.playerRepository.findOneRandomPlayerByClubIdNativeExcludingPlayer(clubId , firstPLayer.getId());

            ArenaGame arenaGame = new ArenaGame();
            arenaGame.setPlayer1(firstPLayer);
            arenaGame.setPlayer2(secondPlayer);
            arenaGame.setWinnerPlayer(null);
            this.arenaGameServiceImplRules.checkIfArenaGamePlayerIsNull(firstPLayer , secondPlayer);

            ArenaGame savedArenaGame = arenaGameRepository.save(arenaGame);

            firstPLayer.getArenaGamesAsPlayer1().add(savedArenaGame);
            firstPLayer.updateArenaPlayed();
            playerRepository.save(firstPLayer);

            secondPlayer.getArenaGamesAsPlayer2().add(savedArenaGame);
            secondPlayer.updateArenaPlayed();
            playerRepository.save(secondPlayer);

            return arenaGame;
        }else {
            throw new RuntimeException("Something went wrong");
        }
    }
    @Transactional
    @Override
    public PlayArenaGameResponse playArenaGame(Long arenaGameId , Long playerId) {

        ArenaGame arenaGame = this.arenaGameRepository.findById(arenaGameId).orElseThrow(() -> new NoArenaGameFoundException("No arena game found"));
        Long player1Id = arenaGame.getPlayer1().getId();
        Long player2Id = arenaGame.getPlayer2().getId();
        if(!Objects.equals(playerId, player1Id) && !Objects.equals(playerId, player2Id)) {
            throw new InvalidPlayerIdException("Invalid player id to assign him as a winner");
        }
        Player player = this.playerRepository.findById(playerId).orElseThrow(() -> new NoPlayerFoundException("No player found to assign as winner player"));
        arenaGame.setWinnerPlayer(player);
        player.getArenaGamesAsWinnerPlayer().add(arenaGame);
        player.updateArenaWins();
        playerRepository.save(player);

        PlayArenaGameResponse playArenaGameResponse = this.modelMapperService.forResponse().map(player , PlayArenaGameResponse.class);
        Long arenaPlayed = player.getArenaPlayed();
        Long arenaWon= player.getArenaWins();
        Double winRate =  (double) arenaWon/arenaPlayed;
        playArenaGameResponse.setWinRate(winRate);

        return playArenaGameResponse;

    }

    @Transactional
    @Override
    public void deleteArenaGameRecord(Long arenaGameId) {

        ArenaGame arenaGame = this.arenaGameRepository.findById(arenaGameId).orElseThrow(()-> new NoArenaGameFoundException("No arena game found to delete"));
        this.arenaGameRepository.deleteById(arenaGameId);

        Player player1 = arenaGame.getPlayer1();
        player1.getArenaGamesAsPlayer1().remove(arenaGame);
        player1.updateArenaPlayed();

        Player player2 = arenaGame.getPlayer2();
        player2.getArenaGamesAsPlayer2().remove(arenaGame);
        player2.updateArenaPlayed();

        Player winnerPlayer = arenaGame.getWinnerPlayer();
        winnerPlayer.getArenaGamesAsWinnerPlayer().remove(arenaGame);
        winnerPlayer.updateArenaWins();
        this.playerRepository.save(winnerPlayer);

    }


    @Override
    public ArenaGameDto convertArenaGameToArenaGameDto(ArenaGame arenaGame) {

        ArenaGameDto arenaGameDto = this.modelMapperService.forResponse().map(arenaGame , ArenaGameDto.class);
        return arenaGameDto;
    }
}
