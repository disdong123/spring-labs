package kr.disdong.spring.labs.jpa.module.user.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kr.disdong.spring.labs.common.token.Token

@Converter
class TokenConverter : AttributeConverter<Token, String> {
    override fun convertToDatabaseColumn(attribute: Token?): String? {
        if (attribute == null) {
            return null
        }

        return attribute.value
    }

    override fun convertToEntityAttribute(s: String?): Token? {
        if (s == null) {
            return null
        }

        return Token(value = s)
    }
}
