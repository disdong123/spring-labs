package kr.disdong.springboot.template.jdbc.module.user.model

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser
import kr.disdong.springboot.template.domain.module.user.model.User
import kr.disdong.springboot.template.jdbc.module.user.model.impl.UserImpl
import org.springframework.data.annotation.Id

class UserEntity(
    @Id
    val id: Long = 0,
    var name: String,
    val phone: String,
) {
    companion object {
        fun of(user: UnsavedUser): UserEntity {
            return UserEntity(
                name = user.name,
                phone = user.phone,
            )
        }
    }

    fun toUser(): User {
        return UserImpl(this)
    }
}
