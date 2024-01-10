package kr.disdong.spring.labs.auth.core.listener.signup.dto

import kr.disdong.spring.labs.domain.module.user.model.impl.PlainUserOauthImpl
import java.util.UUID

data class PreSignupEvent(
    val key: UUID,
    val userOauth: PlainUserOauthImpl
)
