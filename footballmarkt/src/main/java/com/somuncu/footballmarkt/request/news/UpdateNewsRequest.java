package com.somuncu.footballmarkt.request.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsRequest {

    private Long id ;
    private String text;
    private Long playerId;
    private Long clubId;

}
