package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.dtos.user.UserDto;
import com.somuncu.footballmarkt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        UserDto userDto = this.userService.convertUserToUserDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , userDto));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserByName(@RequestParam String userName) {
        User user = this.userService.getUserByName(userName);
        UserDto userDto = this.userService.convertUserToUserDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , userDto));
    }

    @PostMapping("/user/join-community")
    public ResponseEntity<ApiResponse> joinToCommunity(@RequestParam Long communityId ,@AuthenticationPrincipal UserDetails userDetails) {
        this.userService.joinToCommunity(communityId , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" ,  null));
    }
}
