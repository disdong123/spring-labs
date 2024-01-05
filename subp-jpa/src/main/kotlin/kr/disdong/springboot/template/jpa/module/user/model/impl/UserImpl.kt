package kr.disdong.springboot.template.jpa.module.user.model.impl

import kr.disdong.springboot.template.domain.module.user.model.User
import kr.disdong.springboot.template.jpa.module.user.model.UserEntity

class UserImpl(
    private val entity: UserEntity,
) : User {

    override val id: Long
        get() = entity.id
    override var name: String = entity.name
        get() = entity.name
    override val phone: String
        get() = entity.phone

    override fun updateName(name: String) {
        entity.name = name
    }
}
