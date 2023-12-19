package br.com.task.core.domain.model

import br.com.task.utils.extensions.toDateString
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Task(
    @BsonId
    val id: String = ObjectId().toHexString(),
    val title: String = "",
    val description: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val completed: Boolean = false,
    val createAt: String = LocalDateTime.now().toDateString(),
)
