package kr.disdong.springboot.template.domain.module.user.model.impl

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser

class UnsavedUserImpl(
    override val id: Long = 0,
    override var name: String,
    override val phone: String
) : UnsavedUser
