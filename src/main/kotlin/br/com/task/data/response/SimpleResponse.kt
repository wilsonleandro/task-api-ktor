package br.com.task.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: Boolean,
    val message: String,
)
