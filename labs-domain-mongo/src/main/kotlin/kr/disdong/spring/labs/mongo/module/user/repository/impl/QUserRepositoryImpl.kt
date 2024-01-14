package kr.disdong.spring.labs.mongo.module.user.repository.impl

import kr.disdong.spring.labs.domain.module.user.model.PlainQUser
import kr.disdong.spring.labs.domain.module.user.model.QUser
import kr.disdong.spring.labs.domain.module.user.repository.QUserRepository
import kr.disdong.spring.labs.mongo.module.user.model.QUserEntity
import kr.disdong.spring.labs.mongo.module.user.repository.QUserMongoRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class QUserRepositoryImpl(
    private val qUserMongoRepository: QUserMongoRepository,
) : QUserRepository {
    override fun findByUserId(userId: Long): QUser? {
        return qUserMongoRepository.findById(userId).getOrNull()?.toQUser()
    }

    override fun save(user: PlainQUser): QUser {
        return qUserMongoRepository.save(QUserEntity.of(user)).toQUser()
    }
}
