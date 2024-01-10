package kr.disdong.spring.labs.server.common

import kr.disdong.spring.labs.server.ServerApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [ServerApplication::class, EmbeddedRedisConfig::class]
)
@ActiveProfiles("test")
abstract class AbstractSpringBootTest
