package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.PlayerAlreadyExistsException;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.entities.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PlayerServiceImplRules {

    private PlayerRepository playerRepository;

    public void checkIfListEmpty(List<Player> playerList){

        if (playerList == null) {
            throw new NoPlayerFoundException("Player not found");
        }
    }

    public void checkIfPlayerExists(String firstName , String lastName , int age , Double marketValue) {

        if(playerRepository.existsPlayerByFirstNameAndLastNameAndAgeAndMarketValue(firstName,lastName,age,marketValue)) {
            throw new PlayerAlreadyExistsException("Player already exists");
        }

    }




}
