package kr.disdong.springboot.template.jpa.module.user.repository.impl

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser
import kr.disdong.springboot.template.domain.module.user.model.User
import kr.disdong.springboot.template.domain.module.user.repository.UserRepository
import kr.disdong.springboot.template.jpa.module.user.model.UserEntity
import kr.disdong.springboot.template.jpa.module.user.repository.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {
    override fun findByUserId(userId: Long): User? {
        return userJpaRepository.findByUserId(userId)?.toUser()
    }

    override fun save(user: UnsavedUser): User {
        return userJpaRepository.save(UserEntity.of(user)).toUser()
    }
}
