package kr.disdong.spring.labs.auth

import kr.disdong.spring.labs.common.CommonApplication
import kr.disdong.spring.labs.domain.DomainApplication
import kr.disdong.spring.labs.jpa.JpaApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@Import(CommonApplication::class, DomainApplication::class, JpaApplication::class)
@ConfigurationPropertiesScan
@EnableAsync
class AuthApplication
