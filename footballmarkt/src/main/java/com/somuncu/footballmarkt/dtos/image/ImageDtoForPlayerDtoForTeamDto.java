package com.somuncu.footballmarkt.dtos.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDtoForPlayerDtoForTeamDto implements Serializable {

    private String downloadUrl;
}
