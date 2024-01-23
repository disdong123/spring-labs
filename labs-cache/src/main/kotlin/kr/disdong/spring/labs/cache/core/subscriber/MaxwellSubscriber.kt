package kr.disdong.spring.labs.cache.core.subscriber

import com.fasterxml.jackson.databind.ObjectMapper
import kr.disdong.spring.labs.common.logger.logger
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
class MaxwellSubscriber(
    private val objectMapper: ObjectMapper,
): MessageListener {
    private val logger = logger<MaxwellSubscriber>()

    override fun onMessage(message: Message, pattern: ByteArray?) {
        logger.info("message: ${String(message.body)}")
        logger.info("pattern: ${String(pattern!!)}")
    }
}