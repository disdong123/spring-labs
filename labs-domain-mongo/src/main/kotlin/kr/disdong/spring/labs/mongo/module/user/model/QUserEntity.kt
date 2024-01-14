package kr.disdong.spring.labs.mongo.module.user.model

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainQUser
import kr.disdong.spring.labs.domain.module.user.model.QUser
import kr.disdong.spring.labs.mongo.module.user.model.impl.QUserImpl
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
class QUserEntity(
    val id: Long,
    val name: String,
    val phone: String,
    val address: String,
    val addressDetail: String,
    val nickname: String?,
    val type: OauthType,
) {
    companion object {
        fun of(user: PlainQUser): QUserEntity {
            return QUserEntity(
                id = user.id,
                name = user.name,
                phone = user.phone,
                address = user.address,
                addressDetail = user.addressDetail,
                nickname = user.nickname,
                type = user.type,
            )
        }
    }

    fun toQUser(): QUser {
        return QUserImpl(this)
    }
}
