package com.somuncu.footballmarkt.response.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDtoForPlayer {

    private String name;
    private int foundationYear;
    private Double clubValue;

}
