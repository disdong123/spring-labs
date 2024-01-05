package kr.disdong.springboot.template.domain.module.user.model

interface UnsavedUser {
    val id: Long
    var name: String
    val phone: String
}

interface User : UnsavedUser {
    fun updateName(name: String)
}
