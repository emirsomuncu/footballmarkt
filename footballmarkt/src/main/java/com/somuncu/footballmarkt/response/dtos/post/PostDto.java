package com.somuncu.footballmarkt.response.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String text;
    private Date creationTime;
    private String userName;
    private String communityCommunityName;
}
