package com.somuncu.footballmarkt.response.dtos.player;

import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForPlayerValueEstimationGameResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponseForPlayPlayerValueEstimationGameResponse {

    private String fullName;
    private Double marketValue;
    private List<ImageDtoForPlayerValueEstimationGameResponse> images;

}
