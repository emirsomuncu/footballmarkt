package com.somuncu.footballmarkt.dtos.team;

import com.somuncu.footballmarkt.dtos.player.PlayerDtoForTeamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto implements Serializable {

    private Long id;
    private String name;
    private String formation;
    private Double totalMarketValue;
    private List<PlayerDtoForTeamDto> players;
    private String userName;

}
