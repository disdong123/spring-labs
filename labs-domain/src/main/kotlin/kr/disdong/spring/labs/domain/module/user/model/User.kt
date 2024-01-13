package kr.disdong.spring.labs.domain.module.user.model

import kr.disdong.spring.labs.common.token.Token

interface PlainUser : UserData {
    val plainUserOauth: PlainUserOauth
}

interface User : UserData {
    val userOauth: UserOauth

    fun setAccessToken(accessToken: Token)
    fun setRefreshToken(accessToken: Token)
    fun setTokens(accessToken: Token, refreshToken: Token)
    fun removeTokens()
}

interface UserData {
    val id: Long
    val name: String
    val phone: String
    val address: Address
}

class Address(
    val address: String,
    val addressDetail: String,
)
