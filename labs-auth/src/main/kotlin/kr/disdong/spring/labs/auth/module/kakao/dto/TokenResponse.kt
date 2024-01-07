package kr.disdong.spring.labs.auth.module.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.common.token.TokenDeserializer
import java.util.Base64

/**
 * id token, access token, refresh token 모두 포함됩니다.
 */
data class TokenResponse(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("access_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val accessToken: Token,
    @JsonProperty("id_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val idToken: Token? = null,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    @JsonDeserialize(using = TokenDeserializer::class)
    val refreshToken: Token,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,
    @JsonProperty("scope")
    val scope: String? = null, // openId 인경우 openid 포함
) {

    fun decodeIdToken(): IdToken {
        return IdToken.of(String(Base64.getUrlDecoder().decode(toArrayIdToken()[1])))
    }

    private fun toArrayIdToken(): List<String> {
        return idToken!!.value.split(".")
    }
}
