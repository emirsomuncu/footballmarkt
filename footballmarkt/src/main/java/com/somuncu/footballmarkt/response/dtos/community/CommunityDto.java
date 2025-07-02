package com.somuncu.footballmarkt.response.dtos.community;


import com.somuncu.footballmarkt.response.dtos.post.PostDtoForCommunityDto;
import com.somuncu.footballmarkt.response.dtos.user.UserDtoForCommunityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {

    private String communityName;
    private Long numOfMembers;
    private Long numOfPosts;
    private Date creationTime;
    private List<PostDtoForCommunityDto> posts;
    private String creatorOfCommunityName;
    private List<UserDtoForCommunityDto> users;

}
