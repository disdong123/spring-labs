package kr.disdong.spring.labs.domain.module.user.model

interface PlainUser {
    val id: Long
    var name: String
    val phone: String
}

interface User : PlainUser {
    fun updateName(name: String)
}
