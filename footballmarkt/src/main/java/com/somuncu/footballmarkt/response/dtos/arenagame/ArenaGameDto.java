package com.somuncu.footballmarkt.response.dtos.arenagame;

import com.somuncu.footballmarkt.response.dtos.player.PlayerDtoForArenaGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArenaGameDto {

    private PlayerDtoForArenaGameDto player1;
    private PlayerDtoForArenaGameDto player2;
    private PlayerDtoForArenaGameDto winnerPlayer;

}
