package kr.disdong.spring.labs.domain.module.user.model.impl

import kr.disdong.spring.labs.domain.module.user.model.PlainUser

class PlainUserImpl(
    override val id: Long = 0,
    override var name: String,
    override val phone: String
) : PlainUser
