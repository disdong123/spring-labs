package kr.disdong.spring.labs.auth.core.listener.signup.dto

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainQUser
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainQUserImpl

data class SignupEvent(
    val id: Long,
    val name: String,
    val phone: String,
    val address: String,
    val addressDetail: String,
    val nickname: String?,
    val type: OauthType,
) {
    companion object {
        fun of(user: User): SignupEvent {
            return SignupEvent(
                id = user.id,
                name = user.name,
                phone = user.phone,
                address = user.address.address,
                addressDetail = user.address.addressDetail,
                nickname = user.userOauth.nickname,
                type = user.userOauth.type,
            )
        }
    }

    fun toQUser(): PlainQUser {
        return PlainQUserImpl(
            id = this.id,
            name = this.name,
            phone = this.phone,
            address = this.address,
            addressDetail = this.addressDetail,
            nickname = this.nickname,
            type = this.type,
        )
    }
}
