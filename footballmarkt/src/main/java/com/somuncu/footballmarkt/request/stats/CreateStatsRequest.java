package com.somuncu.footballmarkt.request.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStatsRequest {

    private Long id;
    private Long season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long yellowCard;
    private Long redCard;

    private Long playerId;
    private Long clubId;

}
