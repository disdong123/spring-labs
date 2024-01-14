package kr.disdong.spring.labs.jpa.module.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.jpa.module.user.model.QUserEntity
import kr.disdong.spring.labs.jpa.module.user.model.UserEntity
import kr.disdong.spring.labs.jpa.module.user.model.UserOauthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long>, UserCustomJpaRepository

interface UserOauthJpaRepository : JpaRepository<UserOauthEntity, String>

interface UserCustomJpaRepository {
    fun findByUserId(id: Long): UserEntity?
    fun findByPhone(phone: String): UserEntity?
    fun findByOauthIdAndType(oauthId: String, type: OauthType): UserEntity?
    fun findByAccessToken(accessToken: Token): UserEntity?
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

    override fun findByPhone(phone: String): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.phone.eq(phone),
                userEntity.isDeleted.isFalse
            )
            .fetchOne()
    }

    override fun findByOauthIdAndType(oauthId: String, type: OauthType): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.userOauth.id.eq(oauthId),
                userEntity.userOauth.type.eq(type),
                userEntity.isDeleted.isFalse,
            )
            .fetchOne()
    }

    override fun findByAccessToken(accessToken: Token): UserEntity? {
        return jpaQueryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.userOauth.accessToken.eq(accessToken),
                userEntity.isDeleted.isFalse,
            )
            .fetchOne()
    }
}
