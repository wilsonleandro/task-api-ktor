package br.com.task.data.service

import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.data.service.TaskService
import br.com.task.core.domain.model.Task
import br.com.task.core.domain.usecase.ValidateCreateTaskRequest
import br.com.task.data.request.CreateTaskRequest
import br.com.task.data.request.toTask
import br.com.task.data.response.SimpleResponse

class TaskServiceImpl(
    private val repository: TaskRepository,
    private val validateCreateTaskRequest: ValidateCreateTaskRequest,
) : TaskService {
    override suspend fun getTasks(): List<Task> =
        repository.getTasks()

    override suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse {
        val result = validateCreateTaskRequest(createTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = "Invalid Task")
        }
        val insert = repository.insert(task = createTaskRequest.toTask())
        if (!insert) {
            return SimpleResponse(success = false, message = "Cannot save task", statusCode = 400)
        }
        return SimpleResponse(success = true, message = "Task created successfully", statusCode = 201)
    }
}