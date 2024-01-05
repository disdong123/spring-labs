package kr.disdong.springboot.template.jdbc.module.user.repository

import kr.disdong.springboot.template.jdbc.module.user.model.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserJdbcRepository : CrudRepository<UserEntity, Long>
