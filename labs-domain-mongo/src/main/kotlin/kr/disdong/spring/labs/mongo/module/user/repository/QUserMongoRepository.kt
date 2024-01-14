package kr.disdong.spring.labs.mongo.module.user.repository
import kr.disdong.spring.labs.mongo.module.user.model.QUserEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface QUserMongoRepository : MongoRepository<QUserEntity, Long>
