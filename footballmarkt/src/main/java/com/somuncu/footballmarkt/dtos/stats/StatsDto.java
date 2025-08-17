package com.somuncu.footballmarkt.dtos.stats;

import com.somuncu.footballmarkt.dtos.player.PlayerDtoForStatsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {

    private String season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long yellowCard;
    private Long redCard;
    private PlayerDtoForStatsDto player;
    private String clubName;

}
