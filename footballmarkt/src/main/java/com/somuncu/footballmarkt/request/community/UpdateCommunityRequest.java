package com.somuncu.footballmarkt.request.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommunityRequest {

    private Long id;
    private String communityName;
}
