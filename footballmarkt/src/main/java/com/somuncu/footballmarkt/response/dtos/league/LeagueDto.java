package com.somuncu.footballmarkt.response.dtos.league;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDto {

    private String name;
    private Double leagueValue;

}

