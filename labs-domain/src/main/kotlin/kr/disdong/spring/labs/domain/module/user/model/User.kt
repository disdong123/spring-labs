package kr.disdong.spring.labs.domain.module.user.model

interface PlainUser {
    val id: Long
    var name: String
    val phone: String
    val userOauth: UserOauth
}

interface User : PlainUser {
    fun updateName(name: String)
}
