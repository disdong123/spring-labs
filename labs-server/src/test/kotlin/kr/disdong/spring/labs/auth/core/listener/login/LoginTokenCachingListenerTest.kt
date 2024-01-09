package kr.disdong.spring.labs.auth.core.listener.login

import kr.disdong.spring.labs.auth.core.listener.login.dto.LoginEvent
import kr.disdong.spring.labs.common.token.Token
import kr.disdong.spring.labs.server.common.AbstractSpringBootTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher

internal class LoginTokenCachingListenerTest : AbstractSpringBootTest() {
    @Autowired
    private lateinit var sut: ApplicationEventPublisher

    @Test
    fun `application event 발행 테스트`() {
        sut.publishEvent(LoginEvent(1, Token("token")))
    }
}
