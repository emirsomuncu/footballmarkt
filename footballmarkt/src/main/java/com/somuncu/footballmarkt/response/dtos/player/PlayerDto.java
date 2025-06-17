package com.somuncu.footballmarkt.response.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {

    private String firstName;
    private String lastName;
    private String fullName;
    private String nation;
    private int age;
    private String position;
    private String foot;
    private Double marketValue;
    private List<StatsDtoForPlayerDto> stats;
    private String clubName;
    private List<ImageDtoForPlayerDto> images;
    private ClubHistoryDtoForPlayer clubHistory;

}
