package com.somuncu.footballmarkt.dtos.club;

import com.somuncu.footballmarkt.dtos.image.ImageDtoForPlayClubValueEstimationGameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDtoForPlayClubValueEstimationGameResponse {

    private String name;
    private Double clubValue;
    private List<ImageDtoForPlayClubValueEstimationGameResponse> images ;
}
