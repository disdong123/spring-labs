package kr.disdong.spring.labs.server.module.user.dto

import kr.disdong.spring.labs.domain.module.user.model.PlainUser
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserImpl

class CreateUserBody(
    val name: String,
    val phone: String,
) {

    fun toUser(): PlainUser {
        return PlainUserImpl(
            name = name,
            phone = phone,
        )
    }
}
