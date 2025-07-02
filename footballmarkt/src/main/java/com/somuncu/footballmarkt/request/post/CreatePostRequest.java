package com.somuncu.footballmarkt.request.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePostRequest {

    private String text;
    private Long communityId;
}
