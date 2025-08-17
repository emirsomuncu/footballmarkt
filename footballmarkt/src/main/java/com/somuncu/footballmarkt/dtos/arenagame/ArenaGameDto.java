package com.somuncu.footballmarkt.dtos.arenagame;

import com.somuncu.footballmarkt.dtos.player.PlayerDtoForArenaGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArenaGameDto {

    private Long id;
    private PlayerDtoForArenaGameDto player1;
    private PlayerDtoForArenaGameDto player2;
    private PlayerDtoForArenaGameDto winnerPlayer;

}
