package com.somuncu.footballmarkt.response.dtos.news;

import com.somuncu.footballmarkt.response.dtos.club.ClubDtoForNewsDto;
import com.somuncu.footballmarkt.response.dtos.image.ImageDtoForNewsDto;
import com.somuncu.footballmarkt.response.dtos.player.PlayerDtoForNewsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

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
