package kr.disdong.spring.labs.domain.module.user.model.impl

import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainUserOauth

class PlainUserOauthImpl(
    override val id: String,
    override val nickname: String?,
    override val type: OauthType,
    override val accessToken: Token? = null,
    override val refreshToken: Token? = null,
) : PlainUserOauth
