package br.com.task.data.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest(
    val title: String = "",
    val description: String = "",
    val priority: String = "",
)
