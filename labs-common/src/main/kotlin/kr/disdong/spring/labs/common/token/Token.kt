package kr.disdong.spring.labs.common.token

data class Token(
    val value: String,
) {

    fun isPhone(): Boolean {
        return value.startsWith("010")
    }
}
