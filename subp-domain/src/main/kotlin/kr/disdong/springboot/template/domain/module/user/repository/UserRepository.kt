package kr.disdong.springboot.template.domain.module.user.repository

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser
import kr.disdong.springboot.template.domain.module.user.model.User

interface UserRepository {

    fun findByUserId(userId: Long): User?

    fun save(user: UnsavedUser): User
}
