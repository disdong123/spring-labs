package kr.disdong.spring.labs.mongo.module.user.repository.impl

import kr.disdong.spring.labs.domain.module.user.model.OauthType
import kr.disdong.spring.labs.mongo.module.user.model.QUserEntity
import kr.disdong.spring.labs.mongo.module.user.repository.QUserMongoRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class QUserRepositoryImplTest {
    @Autowired
    private lateinit var sut: QUserRepositoryImpl

    @Autowired
    private lateinit var qUserMongoRepository: QUserMongoRepository

    @Test
    fun `mongodb 호출 테스트`() {
        qUserMongoRepository.save(
            QUserEntity(
                id = 1,
                name = "name",
                phone = "phone",
                address = "address",
                addressDetail = "addressDetail",
                nickname = "nickname",
                type = OauthType.KAKAO,
            )
        )

        assertNotNull(sut.findByUserId(1))
    }
}
