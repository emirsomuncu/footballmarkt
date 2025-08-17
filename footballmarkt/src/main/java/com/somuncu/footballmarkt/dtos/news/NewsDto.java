package com.somuncu.footballmarkt.dtos.news;

import com.somuncu.footballmarkt.dtos.club.ClubDtoForNewsDto;
import com.somuncu.footballmarkt.dtos.image.ImageDtoForNewsDto;
import com.somuncu.footballmarkt.dtos.player.PlayerDtoForNewsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {

    private String text;
    private PlayerDtoForNewsDto player;
    private ClubDtoForNewsDto club;
    private List<ImageDtoForNewsDto> images;

}
