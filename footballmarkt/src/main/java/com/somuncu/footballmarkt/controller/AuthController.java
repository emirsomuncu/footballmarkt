package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.core.utiliites.exceptions.auth.InvalidRefreshTokenException;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.user.CreateUserRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.AuthResponse;
import com.somuncu.footballmarkt.security.CustomUserDetailsService;
import com.somuncu.footballmarkt.security.refreshtoken.RefreshToken;
import com.somuncu.footballmarkt.security.refreshtoken.RefreshTokenService;
import com.somuncu.footballmarkt.security.refreshtoken.RefreshTokensRequest;
import com.somuncu.footballmarkt.security.webtoken.FormLogin;
import com.somuncu.footballmarkt.security.webtoken.JwtService;
import com.somuncu.footballmarkt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Ref;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {

        createUserRequest.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        this.userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered successfully" , null));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody FormLogin formLogin) {

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.username() , formLogin.password()));

        if (authentication.isAuthenticated()) {

            String accessToken = this.jwtService.generateToken(userDetailsService.loadUserByUsername(formLogin.username()));
            String refreshToken = this.refreshTokenService.createRefreshToken(userRepository.findUserByEmail(formLogin.username()).get());
            return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(accessToken,refreshToken));

        }else{
            throw new AuthenticationCredentialsNotFoundException("Bad credentials");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshTokens(@RequestBody RefreshTokensRequest refreshTokensRequest) {

        RefreshToken currentRefreshToken = this.refreshTokenService.getRefreshTokenByUserId(refreshTokensRequest.getUserId());

        if(currentRefreshToken.getToken().equals(refreshTokensRequest.getRefreshToken()) &&  !this.refreshTokenService.isRefreshTokenExpired(currentRefreshToken)) {

            User user = this.userRepository.findById(refreshTokensRequest.getUserId()).get();
            String accessToken = this.jwtService.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
            String refreshToken = this.refreshTokenService.createRefreshToken(user);
            return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(accessToken,refreshToken));
        }

        else {
            throw new InvalidRefreshTokenException("Wrong refresh token or refresh token expired(login again)");
        }
    }


}
