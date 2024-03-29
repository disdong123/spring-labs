package kr.disdong.spring.labs.server.common

import kr.disdong.spring.labs.cache.module.user.PlainUserOauthCacheRepository
import kr.disdong.spring.labs.server.ServerApplication
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [ServerApplication::class]
)
@ActiveProfiles("test")
abstract class IntegrationTest {

    @MockBean
    protected lateinit var plainUserOauthCacheRepository: PlainUserOauthCacheRepository

    @BeforeEach
    fun setUp() {
        doNothing().whenever(plainUserOauthCacheRepository).save(any(), any())
    }

    companion object {
        private val redis = GenericContainer(DockerImageName.parse("redis:alpine")).withExposedPorts(6379)
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            redis.start()
        }
    }
}
