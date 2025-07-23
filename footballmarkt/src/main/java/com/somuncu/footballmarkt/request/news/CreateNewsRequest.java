package com.somuncu.footballmarkt.request.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {

    private String text;
    private Long playerId;
    private Long clubId;
    private List<Long> imagesIds;

}
