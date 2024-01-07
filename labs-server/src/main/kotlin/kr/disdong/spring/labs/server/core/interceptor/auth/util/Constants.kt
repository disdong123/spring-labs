package kr.disdong.spring.labs.server.core.interceptor.auth.util

object Constants {
    val BYPASS_LIST = listOf(
        "/v1/auth/kakao/login/**",
    )
}
