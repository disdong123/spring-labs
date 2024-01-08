package kr.disdong.spring.labs.auth.module.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.oauth.base-url")
data class KakaoOauthBaseUrlProperties(
    val kauth: String,
    val kapi: String,
)

@ConfigurationProperties(prefix = "kakao.oauth.v1")
data class KakaoOauthProperties(
    val clientId: String,
    val loginRedirectUri: String,
    val logoutRedirectUri: String,
)
