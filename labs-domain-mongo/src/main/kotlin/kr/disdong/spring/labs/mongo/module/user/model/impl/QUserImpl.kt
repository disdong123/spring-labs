package kr.disdong.spring.labs.mongo.module.user.model.impl

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.QUser
import kr.disdong.spring.labs.mongo.module.user.model.QUserEntity

class QUserImpl(
    private val entity: QUserEntity,
) : QUser {

    override val id: Long
        get() = entity.id
    override val name: String
        get() = entity.name
    override val phone: String
        get() = entity.phone
    override val address: String
        get() = entity.address
    override val addressDetail: String
        get() = entity.addressDetail
    override val nickname: String?
        get() = entity.nickname
    override val type: OauthType
        get() = entity.type
}
