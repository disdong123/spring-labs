package kr.disdong.spring.labs.server.module.user.dto

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.QUser

class UserResponse(
    val id: Long,
    val name: String,
    val phone: String,
    val address: String,
    val addressDetail: String,
    val nickname: String?,
    val type: OauthType,
) {
    companion object {
        fun of(user: QUser): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                phone = user.phone,
                address = user.address,
                addressDetail = user.addressDetail,
                nickname = user.nickname,
                type = user.type,
            )
        }
    }
}
