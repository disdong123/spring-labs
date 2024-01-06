package kr.disdong.spring.labs.common.token

class JwtHeader(
    val typ: String = "JWT",
    val alg: String = "HS256",
)
