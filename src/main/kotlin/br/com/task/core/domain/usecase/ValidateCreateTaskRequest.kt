package br.com.task.core.domain.usecase

import br.com.task.data.request.CreateTaskRequest

interface ValidateCreateTaskRequest {
    operator fun invoke(request: CreateTaskRequest): Boolean
}

class ValidateCreateTaskRequestImpl : ValidateCreateTaskRequest {
    override fun invoke(request: CreateTaskRequest): Boolean {
        return !(request.title.isEmpty() ||
                request.description.isEmpty() ||
                request.priority.isEmpty() ||
                request.dueDate.isEmpty())
    }
}
