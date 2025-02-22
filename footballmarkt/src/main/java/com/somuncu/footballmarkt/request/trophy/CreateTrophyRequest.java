package com.somuncu.footballmarkt.request.trophy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTrophyRequest {

    private Long id;
    private String name;
    private String season;
    private Long leagueId;
    private Long clubId;

}
