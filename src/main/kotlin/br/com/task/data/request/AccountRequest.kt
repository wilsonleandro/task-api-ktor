package br.com.task.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AccountRequest(
    val email: String,
    val password: String,
)
