package com.somuncu.footballmarkt.security.refreshtoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokensRequest {

    private Long userId;
    private String refreshToken;

}
