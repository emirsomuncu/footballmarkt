package com.somuncu.footballmarkt.response.dtos.club;

import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForClubDto;
import com.somuncu.footballmarkt.response.dtos.player.PlayerDtoForClubDto;
import com.somuncu.footballmarkt.response.dtos.trophy.TrophyDtoForClubDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto {

    private String name;
    private int foundationYear;
    private Double clubValue;
    private List<PlayerDtoForClubDto> players;
    private String leagueName;
    private List<ImageDtoForClubDto> images;
    private List<TrophyDtoForClubDto> trophies;

}
