package kr.disdong.spring.labs.auth.module.kakao.dto

data class IdTokenUserInfo(
    val sub: String,
    val name: String?,
    val nickname: String?,
    val picture: String?,
    val email: String?,
    val email_verified: Boolean?,
    val gender: String?,
    val birthdate: String?,
    val phone_number: String?,
    val phone_number_verified: Boolean?,
)
