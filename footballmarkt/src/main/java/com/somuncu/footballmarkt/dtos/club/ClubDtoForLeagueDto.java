package com.somuncu.footballmarkt.dtos.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDtoForLeagueDto {

    private String name;
    private Double clubValue;
}
