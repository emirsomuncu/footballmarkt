package com.somuncu.footballmarkt.security.refreshtoken;

import com.somuncu.footballmarkt.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.token.expires.in}")
    private Long expireSecond;

    public String createRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByUserId(user.getId());
        if (refreshToken == null) {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSecond)));

        this.refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();

    }

    public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().before(Date.from(Instant.now()));
    }


    public RefreshToken getRefreshTokenByUserId(Long userId) {
        RefreshToken refreshToken = this.refreshTokenRepository.findRefreshTokenByUserId(userId);
        return refreshToken;
    }

}
