package kr.disdong.spring.labs.server.common

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class WireMockTest {
    protected val kakaoServer = WireMockServer(9090)

    @LocalServerPort
    protected var port = 0
    protected var client: WebTestClient? = null

    @BeforeAll
    fun beforeAll() {
        kakaoServer.start()
    }

    @BeforeEach
    fun setup(context: ApplicationContext) {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:$port")
            .build()
    }

    @AfterEach
    fun afterEach() {
        kakaoServer.resetAll()
    }

    @AfterAll
    fun afterAll() {
        kakaoServer.stop()
    }
}
