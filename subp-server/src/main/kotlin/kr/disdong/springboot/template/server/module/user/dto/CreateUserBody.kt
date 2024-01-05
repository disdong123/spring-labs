package kr.disdong.springboot.template.server.module.user.dto

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser
import kr.disdong.springboot.template.domain.module.user.model.impl.UnsavedUserImpl

class CreateUserBody(
    val name: String,
    val phone: String,
) {

    fun toUser(): UnsavedUser {
        return UnsavedUserImpl(
            name = name,
            phone = phone,
        )
    }
}
