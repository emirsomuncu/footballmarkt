package com.somuncu.footballmarkt.response.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForArenaGameDto {

    private String fullName;
    private Double marketValue;

}
