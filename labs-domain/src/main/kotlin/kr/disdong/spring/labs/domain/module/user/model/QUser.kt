package kr.disdong.spring.labs.domain.module.user.model

interface PlainQUser : QUserData

interface QUser : QUserData

interface QUserData {
    val id: Long
    val name: String
    val phone: String
    val address: String
    val addressDetail: String
    val nickname: String?
    val type: OauthType
}
