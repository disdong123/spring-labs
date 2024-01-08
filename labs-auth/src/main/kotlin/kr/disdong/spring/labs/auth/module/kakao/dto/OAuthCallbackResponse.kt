package kr.disdong.spring.labs.auth.module.kakao.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.common.token.TokenDeserializer

/**
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-response
 * @property code
 * @property state
 * @property error
 * @property error_description
 */
// @Schema(description = "login 요청값입니다.")
data class OAuthCallbackResponse(
    @JsonDeserialize(using = TokenDeserializer::class)
    val code: Token?,
    val state: String? = null,
    val error: String? = null,
    val error_description: String? = null,
    val redirectUri: String? = null, // client 에서 kakao 로그인으로 redirect 할 떄 사용됩니다.
) {

    fun isAccessDenied(): Boolean {
        return this.error == "access_denied"
    }
}
