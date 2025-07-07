package com.somuncu.footballmarkt.response.dtos.player;

import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForPlayerDtoForPlayerValueEstimationGameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForPlayerValueEstimationGameDto {

    private String fullName;
    private List<ImageDtoForPlayerDtoForPlayerValueEstimationGameDto> images;

}
