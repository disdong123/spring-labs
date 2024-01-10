package kr.disdong.spring.labs.server.module.user.dto

import kr.disdong.spring.labs.domain.module.user.model.Address
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserImpl
import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl

data class UserPersonalInfoBody(
    val name: String,
    val phone: String,
    val address: String,
    val addressDetail: String,
) {
    fun toPlainUser(userOauth: PlainUserOauthImpl): PlainUserImpl {
        return PlainUserImpl(
            name = this.name,
            phone = this.phone,
            address = Address(this.address, this.addressDetail),
            plainUserOauth = userOauth,
        )
    }
}
