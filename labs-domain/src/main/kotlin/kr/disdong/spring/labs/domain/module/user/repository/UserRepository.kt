package kr.disdong.spring.labs.domain.module.user.repository

import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainQUser
import kr.disdong.spring.labs.domain.module.user.model.PlainUser
import kr.disdong.spring.labs.domain.module.user.model.QUser
import kr.disdong.spring.labs.domain.module.user.model.User

interface UserRepository {

    fun findAll(): List<User>
    fun findByUserId(userId: Long): User?
    fun findByPhone(phone: String): User?
    fun findByOauthIdAndType(oauthId: String, type: OauthType): User?
    fun findByAccessToken(accessToken: Token): User?
    fun save(user: PlainUser): User
    fun deleteAll()
}

interface QUserRepository {
    fun findByUserId(userId: Long): QUser?

    fun save(user: PlainQUser): QUser
}
