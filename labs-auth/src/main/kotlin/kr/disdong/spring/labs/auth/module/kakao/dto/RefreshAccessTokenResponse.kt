package kr.disdong.spring.labs.auth.module.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty
import kr.disdong.spring.labs.common.token.Token

data class RefreshAccessTokenResponse(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("access_token")
    val accessToken: Token,
    @JsonProperty("id_token")
    val idToken: String?,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: Token?,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int?,
)
