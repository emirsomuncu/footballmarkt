package com.somuncu.footballmarkt.dtos.trophy;

import com.somuncu.footballmarkt.dtos.image.ImageDtoForTrophyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrophyDto {

    private String name;
    private String season;
    private List<ImageDtoForTrophyDto> images;
    private String leagueName;
    private String clubName;

}
