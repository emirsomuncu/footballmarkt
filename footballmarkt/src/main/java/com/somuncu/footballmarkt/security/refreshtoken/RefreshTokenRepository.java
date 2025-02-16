package com.somuncu.footballmarkt.security.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Long> {

    public RefreshToken findRefreshTokenByUserId(Long userId);
}
