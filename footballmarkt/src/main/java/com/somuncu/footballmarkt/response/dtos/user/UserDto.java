package com.somuncu.footballmarkt.response.dtos.user;

import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.response.dtos.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name ;
    private List<PostDto> posts;
    private List<CommunityDtoForUserDto> communityOwnerShip;
    private List<CommunityDtoForUserDto> communities;

}
