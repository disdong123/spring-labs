package kr.disdong.spring.labs.auth.module.kakao.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.common.token.TokenSerializer
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import java.util.UUID

@Schema(description = "login 응답값입니다.")
class LoginResponse(
    val loginPhase: LoginPhase?,
    val signupPhase: SignupPhase?,
) {
    companion object {
        fun signupPhase(id: UUID): LoginResponse {
            return LoginResponse(
                loginPhase = null,
                signupPhase = SignupPhase(
                    signupId = id,
                ),
            )
        }
    }
}

class LoginPhase(
    val name: String,
    val phone: String,
    val oauthType: OauthType,
    val oauthId: String,
    val oauthNickname: String?,
    @Schema(description = "access token", type = "String")
    @JsonSerialize(using = TokenSerializer::class)
    val accessToken: Token
)

class SignupPhase(
    val signupId: UUID,
)
