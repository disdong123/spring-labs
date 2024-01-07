package kr.disdong.spring.labs.auth.fixture.user

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setNotNull
import kr.disdong.spring.labs.auth.fixture.FixtureUtil
import kr.disdong.spring.labs.domain.module.user.model.User
import kr.disdong.spring.labs.domain.module.user.model.UserOauth

object UserFixture {
    fun any(): User = FixtureUtil.monkey().giveMeBuilder<User>()
        .sample()

    fun any(userOauth: UserOauth) = FixtureUtil.monkey().giveMeBuilder<User>()
        .sample()
}

object UserOauthFixture {
    fun any(): UserOauth = FixtureUtil.monkey().giveMeBuilder<UserOauth>()
        .setNotNull(UserOauth::accessToken)
        .setNotNull(UserOauth::refreshToken)
        .sample()
}
