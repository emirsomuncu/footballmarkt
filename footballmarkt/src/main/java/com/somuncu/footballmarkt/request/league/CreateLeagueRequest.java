package com.somuncu.footballmarkt.request.league;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLeagueRequest {

    private Long leagueId;
    private String name;
    private List<Long> imagesIds;

}
