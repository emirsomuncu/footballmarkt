package com.somuncu.footballmarkt.response.dtos.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDtoForPlayerDto {

    private Long id ;
    private String season;
    private Long goal;
    private Long assist;
    private Long playedMatch;
    private Long firstEleven;
    private Long playedMinutes;
    private Long yellowCard;
    private Long redCard;
    private String clubName;

}
