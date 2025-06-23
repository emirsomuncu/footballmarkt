package com.somuncu.footballmarkt.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayArenaGameResponse {

    private String fullName;
    private Long arenaPlayed ;
    private Long arenaWins ;
    private Double winRate;
}
