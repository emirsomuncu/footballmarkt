package com.somuncu.footballmarkt.response.dtos.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDtoForNewsDto {

    private String name;
    private Double clubValue;
    private String leagueName;

}
