package com.somuncu.footballmarkt.response.dtos.player;

import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForPlayerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoForClubDto {

    private Long id ;
    private String firstName;
    private String lastName;
    private String nation;
    private int age;
    private String position;
    private String foot;
    private Double marketValue;
    private List<ImageDtoForPlayerDto> images;

}
