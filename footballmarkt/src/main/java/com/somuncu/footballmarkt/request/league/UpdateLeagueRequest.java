package com.somuncu.footballmarkt.request.league;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeagueRequest {

    private Long leagueId;
    private String name;

}
