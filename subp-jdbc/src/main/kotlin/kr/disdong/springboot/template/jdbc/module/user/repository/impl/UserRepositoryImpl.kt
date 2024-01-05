package kr.disdong.springboot.template.jdbc.module.user.repository.impl

import kr.disdong.springboot.template.domain.module.user.model.UnsavedUser
import kr.disdong.springboot.template.domain.module.user.model.User
import kr.disdong.springboot.template.domain.module.user.repository.UserRepository
import kr.disdong.springboot.template.jdbc.module.user.model.UserEntity
import kr.disdong.springboot.template.jdbc.module.user.repository.UserJdbcRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val userJdbcRepository: UserJdbcRepository,
) : UserRepository {
    override fun findByUserId(userId: Long): User? {
        return userJdbcRepository.findById(userId).getOrNull()?.toUser()
    }

    override fun save(user: UnsavedUser): User {
        return userJdbcRepository.save(UserEntity.of(user)).toUser()
    }
}
