package com.somuncu.footballmarkt.request.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeamRequest {

    private Long id;
    private String name;
    private String formation;
    private List<Long> playersIdsList;
}
