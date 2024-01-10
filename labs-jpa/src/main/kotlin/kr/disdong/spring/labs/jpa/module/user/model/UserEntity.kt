package kr.disdong.spring.labs.jpa.module.user.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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

    @Column(
        nullable = false,
        unique = false,
        length = 60,
    )
    val address: String,

    @Column(
        nullable = false,
        unique = false,
        length = 60,
    )
    val addressDetail: String,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "oauth_id")
    var userOauth: UserOauthEntity,
) : BaseEntity() {
    companion object {
        fun of(user: PlainUser): UserEntity {
            return UserEntity(
                name = user.name,
                phone = user.phone,
                address = user.address.address,
                addressDetail = user.address.addressDetail,
                userOauth = UserOauthEntity.of(user.plainUserOauth),
            )
        }
    }

    fun toUser(): User {
        return UserImpl(this)
    }
}
