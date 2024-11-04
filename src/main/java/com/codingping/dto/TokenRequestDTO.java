package com.codingping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenRequestDTO {
    private String jwtToken;
    private String refreshToken;
    private String kakaoId;

    @Builder
    public TokenRequestDTO(String jwtToken, String refreshToken, String kakaoId) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.kakaoId = kakaoId;
    }
}
