package kr.disdong.spring.labs.jpa.module.user.repository.impl

import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainUser
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.domain.module.user.repository.UserRepository
import kr.disdong.spring.labs.jpa.module.user.model.UserEntity
import kr.disdong.spring.labs.jpa.module.user.repository.UserJpaRepository
import kr.disdong.spring.labs.jpa.module.user.repository.UserOauthJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
    private val userOauthJpaRepository: UserOauthJpaRepository,
) : UserRepository {
    override fun findAll(): List<User> {
        return userJpaRepository.findAll().map { it.toUser() }
    }

    override fun findByUserId(userId: Long): User? {
        return userJpaRepository.findByUserId(userId)?.toUser()
    }

    override fun findByPhone(phone: String): User? {
        return userJpaRepository.findByPhone(phone)?.toUser()
    }

    override fun findByOauthIdAndType(oauthId: String, type: OauthType): User? {
        return userJpaRepository.findByOauthIdAndType(oauthId, type)?.toUser()
    }

    override fun findByAccessToken(accessToken: Token): User? {
        return userJpaRepository.findByAccessToken(accessToken)?.toUser()
    }

    override fun save(user: PlainUser): User {
        return userJpaRepository.save(UserEntity.of(user)).toUser()
    }

    override fun deleteAll() {
        userJpaRepository.deleteAll()
        userOauthJpaRepository.deleteAll()
    }
}
