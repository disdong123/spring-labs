package kr.disdong.spring.labs.common.generator

import java.util.UUID

interface IdGenerator {

    fun generate(): UUID
}

object UuidGenerator : IdGenerator {

    override fun generate(): UUID {
        return UUID.randomUUID()
    }
}
