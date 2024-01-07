package kr.disdong.spring.labs.domain.module.user.model

import kr.disdong.spring.labs.common.token.Token

interface PlainUserOauth : UserOauthData

interface UserOauth : UserOauthData {
    fun setToken(accessToken: Token, refreshToken: Token)
}

interface UserOauthData {
    val id: String
    val nickname: String?
    val type: OauthType
    val accessToken: Token?
    val refreshToken: Token?
}

enum class OauthType(val value: String) {
    KAKAO("kakao");
}
