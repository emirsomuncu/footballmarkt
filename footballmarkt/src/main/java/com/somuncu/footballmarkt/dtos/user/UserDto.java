package com.somuncu.footballmarkt.dtos.user;

import com.somuncu.footballmarkt.dtos.post.PostDto;
import com.somuncu.footballmarkt.dtos.user.CommunityDtoForUserDto;
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
