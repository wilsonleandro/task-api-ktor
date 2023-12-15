package br.com.task.data.request

import br.com.task.core.domain.model.Task

data class CreateTaskRequest(
    val title: String = "",
    val description: String = "",
    val priority: String = "",
    val dueDate: String = "",
)

fun CreateTaskRequest.toTask(): Task {
    return Task(
        title = this.title,
        description = this.description,
        priority = this.priority,
        dueDate = this.dueDate,
    )
}
