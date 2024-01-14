package kr.disdong.spring.labs.jpa.module.user.model.impl

import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.UserOauth
import kr.disdong.spring.labs.jpa.module.user.model.UserOauthEntity

class UserOauthImpl(
    private val entity: UserOauthEntity,
) : UserOauth {

    override val id: String
        get() = entity.id
    override val nickname: String?
        get() = entity.nickname
    override val type: OauthType
        get() = entity.type
    override val accessToken: Token?
        get() = entity.accessToken
    override val refreshToken: Token?
        get() = entity.refreshToken

    override fun setToken(accessToken: Token, refreshToken: Token) {
        entity.accessToken = accessToken
        entity.refreshToken = refreshToken
    }
}
