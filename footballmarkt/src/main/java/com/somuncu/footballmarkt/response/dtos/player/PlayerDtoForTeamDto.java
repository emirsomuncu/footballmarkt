package com.somuncu.footballmarkt.response.dtos.player;

import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForPlayerDto;
import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForPlayerDtoForTeamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForTeamDto implements Serializable {

    private String fullName;
    private String position;
    private Double marketValue;
    private List<ImageDtoForPlayerDtoForTeamDto> images;

}
