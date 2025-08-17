package com.somuncu.footballmarkt.dtos.club;

import com.somuncu.footballmarkt.dtos.image.ImageDtoForClubDtoForClubValueEstimationGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDtoForClubValueEstimationGameDto {

    private String name;
    private List<ImageDtoForClubDtoForClubValueEstimationGameDto> images ;

}
