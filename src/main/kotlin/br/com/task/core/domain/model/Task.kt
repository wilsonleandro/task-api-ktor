package br.com.task.core.domain.model

import br.com.task.utils.extensions.toDateString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import java.time.LocalDateTime

@Serializable
data class Task(
    @SerialName("_id")
    val _id: String = ObjectId().toHexString(),
    val title: String = "",
    val description: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val completed: Boolean = false,
    val createAt: String = LocalDateTime.now().toDateString(),
    val customerId: String = "",
)
