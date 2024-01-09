package kr.disdong.spring.labs.auth.core.listener.login.dto

import kr.disdong.spring.labs.common.token.Token

data class LoginEvent(
    val userId: Long,
    val accessToken: Token,
)
