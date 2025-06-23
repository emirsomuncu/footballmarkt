package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.entities.Player;
import org.springframework.stereotype.Service;

@Service
public class ArenaGameServiceImplRules {

    public void checkIfArenaGamePlayerIsNull(Player player1 , Player player2) {

        if(player1 == null || player2 == null) {
            throw new NoPlayerFoundException("No player found to assign as arena game player");
        }

    }

}
