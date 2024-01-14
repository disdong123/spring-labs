package kr.disdong.spring.labs.domain.module.user.model.impl

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainQUser

class PlainQUserImpl(
    override val id: Long,
    override val name: String,
    override val phone: String,
    override val address: String,
    override val addressDetail: String,
    override val nickname: String?,
    override val type: OauthType
) : PlainQUser
