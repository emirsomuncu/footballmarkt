package com.somuncu.footballmarkt.response.dtos.clubvalueestimationgame;

import com.somuncu.footballmarkt.response.dtos.club.ClubDtoForClubValueEstimationGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubValueEstimationGameDto {

    private Long id;
    private ClubDtoForClubValueEstimationGameDto clubOne;
    private ClubDtoForClubValueEstimationGameDto clubTwo;

}
