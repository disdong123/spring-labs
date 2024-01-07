package kr.disdong.spring.labs.server.fixture.user

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.server.fixture.FixtureUtil

object UserFixture {
    fun any(): User = FixtureUtil.monkey().giveMeBuilder<User>()
        .sample()
}
