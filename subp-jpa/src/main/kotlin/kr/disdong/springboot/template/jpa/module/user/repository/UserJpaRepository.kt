package kr.disdong.springboot.template.jpa.module.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.springboot.template.jpa.module.user.model.QUserEntity
import kr.disdong.springboot.template.jpa.module.user.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long>, UserCustomJpaRepository

interface UserCustomJpaRepository {
    fun findByUserId(id: Long): UserEntity?
}

class UserJpaRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserCustomJpaRepository {

    private val userEntity = QUserEntity.userEntity
    override fun findByUserId(id: Long): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.id.eq(id),
                userEntity.isDeleted.isFalse
            )
            .fetchOne()
    }
}
