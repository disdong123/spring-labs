package kr.disdong.spring.labs.server

import kr.disdong.spring.labs.auth.AuthApplication
import kr.disdong.spring.labs.cache.CacheApplication
import kr.disdong.spring.labs.domain.DomainApplication
import kr.disdong.spring.labs.jpa.JpaApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DomainApplication::class, JpaApplication::class, AuthApplication::class, CacheApplication::class)
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
