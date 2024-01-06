package br.com.task.core.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Customer(
    @SerialName("_id")
    val _id: String = ObjectId().toHexString(),
    val name: String,
    val email: String,
    val password: String,
)
