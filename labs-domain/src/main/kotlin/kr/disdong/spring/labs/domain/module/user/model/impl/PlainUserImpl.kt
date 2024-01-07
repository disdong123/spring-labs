package kr.disdong.spring.labs.domain.module.user.model.impl

import kr.disdong.spring.labs.domain.module.user.model.PlainUser
import kr.disdong.spring.labs.domain.module.user.model.PlainUserOauth

class PlainUserImpl(
    override val id: Long = 0,
    override var name: String? = null,
    override val phone: String? = null,
    override val plainUserOauth: PlainUserOauth,
) : PlainUser
