package com.somuncu.footballmarkt.dtos.league;

import com.somuncu.footballmarkt.dtos.club.ClubDtoForLeagueDto;
import com.somuncu.footballmarkt.dtos.image.ImageDtoForLeagueDto;
import com.somuncu.footballmarkt.dtos.trophy.TrophyDtoForLeagueDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueDto {

    private String name;
    private Double leagueValue;
    private List<ClubDtoForLeagueDto> clubs;
    private List<ImageDtoForLeagueDto> images;
    private List<TrophyDtoForLeagueDto> trophies;
}

