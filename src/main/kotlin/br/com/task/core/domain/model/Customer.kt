package br.com.task.core.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Customer(
    @BsonId
    val id: String = ObjectId().toHexString(),
    val name: String,
    val email: String,
    val password: String,
)
