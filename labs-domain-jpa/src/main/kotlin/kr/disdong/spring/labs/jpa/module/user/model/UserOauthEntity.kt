package kr.disdong.spring.labs.jpa.module.user.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.domain.module.user.model.PlainUserOauth
import kr.disdong.spring.labs.domain.module.user.model.UserOauth
import kr.disdong.spring.labs.jpa.common.model.BaseEntity
import kr.disdong.spring.labs.jpa.module.user.converter.TokenConverter
import kr.disdong.spring.labs.jpa.module.user.model.impl.UserOauthImpl
import org.hibernate.annotations.Comment

@Entity(name = "user_oauth")
class UserOauthEntity(
    // oauth token 에서 유저를 구분할 수 있는 유일값입니다.
    // kakao 는 sub 값 입니다.
    @Comment("oauth token 에서 유저를 구분할 수 있는 유일값")
    @Id
    var id: String,

    @Comment("각 서비스에서 사용하는 nickname")
    @Column(
        nullable = true,
        unique = false,
        length = 200,
    )
    var nickname: String?,

    @Comment("oauth 타입")
    @Enumerated(value = EnumType.STRING)
    @Column(
        nullable = false,
        unique = false,
        length = 10,
    )
    var type: OauthType,

    @Comment("access token. redis?")
    @Column(
        nullable = true,
        unique = true,
        length = 255,
    )
    @Convert(converter = TokenConverter::class)
    var accessToken: Token? = null,

    @Comment("refresh token. redis?")
    @Column(
        nullable = true,
        unique = true,
        length = 255,
    )
    @Convert(converter = TokenConverter::class)
    var refreshToken: Token? = null
) : BaseEntity() {
    companion object {
        fun of(userOauth: PlainUserOauth): UserOauthEntity {
            return UserOauthEntity(
                id = userOauth.id,
                nickname = userOauth.nickname,
                type = userOauth.type,
                accessToken = userOauth.accessToken,
                refreshToken = userOauth.refreshToken,
            )
        }
    }

    fun toUserOauth(): UserOauth {
        return UserOauthImpl(this)
    }
}
