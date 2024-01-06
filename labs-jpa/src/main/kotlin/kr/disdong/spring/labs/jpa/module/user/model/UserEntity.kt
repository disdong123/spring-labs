package kr.disdong.spring.labs.jpa.module.user.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kr.disdong.spring.labs.domain.module.user.model.PlainUser
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.jpa.common.model.BaseEntity
import kr.disdong.spring.labs.jpa.module.user.model.impl.UserImpl

@Entity(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(
        nullable = false,
        unique = false,
        length = 100,
    )
    var name: String,

    @Column(
        nullable = false,
        unique = false,
        length = 20,
    )
    val phone: String,
) : BaseEntity() {
    companion object {
        fun of(user: PlainUser): UserEntity {
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
