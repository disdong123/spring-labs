package kr.disdong.spring.labs.domain.module.user.model

import kr.disdong.spring.labs.common.token.Token

interface PlainUserOauth {
    val id: String
    val nickname: String
    val type: OauthType
    val accessToken: Token?
    val refreshToken: Token?
}

interface UserOauth : PlainUserOauth

enum class OauthType(val value: String) {
    KAKAO("kakao");
}
