package com.somuncu.footballmarkt.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForNewsDto {

    private String fullName;
    private Double marketValue;
    private String clubName;
}
