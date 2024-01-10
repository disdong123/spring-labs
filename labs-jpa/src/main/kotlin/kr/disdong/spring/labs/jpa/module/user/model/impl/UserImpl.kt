package kr.disdong.spring.labs.jpa.module.user.model.impl

import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.Address
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.domain.module.user.model.UserOauth
import kr.disdong.spring.labs.jpa.module.user.model.UserEntity

class UserImpl(
    private val entity: UserEntity,
) : User {

    override val id: Long
        get() = entity.id
    override var name: String = entity.name
        get() = entity.name
    override val phone: String
        get() = entity.phone
    override val userOauth: UserOauth
        get() = entity.userOauth.toUserOauth()

    override val address: Address
        get() = Address(
            address = entity.address,
            addressDetail = entity.addressDetail,
        )

    override fun updateName(name: String) {
        entity.name = name
    }

    override fun setAccessToken(accessToken: Token) {
        entity.userOauth.accessToken = accessToken
    }

    override fun setRefreshToken(refreshToken: Token) {
        entity.userOauth.refreshToken = refreshToken
    }

    override fun setTokens(accessToken: Token, refreshToken: Token) {
        setAccessToken(accessToken)
        setRefreshToken(refreshToken)
    }

    override fun removeTokens() {
        entity.userOauth.accessToken = null
        entity.userOauth.refreshToken = null
    }
}
