package com.somuncu.footballmarkt.response.dtos.trophy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrophyDtoForLeagueDto {

    private String season;
    private String clubName;

}
