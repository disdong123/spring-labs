package kr.disdong.spring.labs.auth.common

import kr.disdong.spring.labs.auth.AuthApplication
import kr.disdong.spring.labs.auth.core.listener.login.LoginLoggingListener
import kr.disdong.spring.labs.auth.core.listener.signup.PreSignupCacheListener
import kr.disdong.spring.labs.auth.core.listener.signup.SignupSmsListener
import kr.disdong.spring.labs.auth.module.kakao.KakaoClient
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [AuthApplication::class, EmbeddedRedisConfig::class]
)
@ActiveProfiles("test")
abstract class AbstractSpringBootTest {

    @MockBean
    protected lateinit var kakaoClient: KakaoClient

    @MockBean
    protected lateinit var applicationEventPublisher: ApplicationEventPublisher

    @MockBean
    protected lateinit var preSignupCacheListener: PreSignupCacheListener

    @MockBean
    protected lateinit var signupSmsListener: SignupSmsListener

    @MockBean
    protected lateinit var loginLoggingListener: LoginLoggingListener

    @BeforeEach
    fun setUp() {
        doNothing().whenever(preSignupCacheListener).handle(any())
        doNothing().whenever(signupSmsListener).handle(any())
        doNothing().whenever(loginLoggingListener).handle(any())
    }
}
