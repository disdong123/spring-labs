package kr.disdong.spring.labs.jpa.common.model

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Column
    var isDeleted: Boolean = false,

    @Column
    var createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column
    var updatedAt: ZonedDateTime = ZonedDateTime.now(),
) {
    @PreUpdate
    fun beforeUpdate() {
        this.updatedAt = ZonedDateTime.now()
    }
}
