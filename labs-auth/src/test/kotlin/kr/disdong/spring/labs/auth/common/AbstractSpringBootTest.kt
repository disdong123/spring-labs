package kr.disdong.spring.labs.auth.common

import kr.disdong.spring.labs.auth.AuthApplication
import kr.disdong.spring.labs.auth.module.kakao.KakaoClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [AuthApplication::class, EmbeddedRedisConfig::class]
)
@ActiveProfiles("test")
abstract class AbstractSpringBootTest {

    @MockBean
    protected lateinit var kakaoClient: KakaoClient
}
