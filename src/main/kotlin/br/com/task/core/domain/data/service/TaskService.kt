package br.com.task.core.domain.data.service

import br.com.task.core.domain.model.Task
import br.com.task.data.request.CreateTaskRequest
import br.com.task.data.response.SimpleResponse

interface TaskService {
    suspend fun getTasks(): List<Task>
    suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse
}