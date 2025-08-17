package com.somuncu.footballmarkt.dtos.trophy;

import com.somuncu.footballmarkt.dtos.image.ImageDtoForTrophyDtosClubDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrophyDtoForClubDto {

    private String name;
    private String season;
    private List<ImageDtoForTrophyDtosClubDto> images;
    private String leagueName;

}
