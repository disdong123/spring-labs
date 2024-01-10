package kr.disdong.spring.labs.auth.module.kakao.extension

import kr.disdong.spring.labs.auth.module.kakao.dto.LoginPhase
import kr.disdong.spring.labs.auth.module.kakao.dto.LoginResponse
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.User

fun User.toLoginResponse(accessToken: Token): LoginResponse {
    return LoginResponse(
        loginPhase = LoginPhase(
            name = this.name,
            phone = this.phone,
            oauthId = this.userOauth.id,
            oauthNickname = this.userOauth.nickname,
            oauthType = this.userOauth.type,
            accessToken = accessToken,
        ),
        signupPhase = null,
    )
}
