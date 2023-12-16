package br.com.task.data.service

import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.data.service.TaskService
import br.com.task.core.domain.model.Task
import br.com.task.core.domain.usecase.ValidateCreateTaskRequest
import br.com.task.core.domain.usecase.ValidateUpdateTaskRequest
import br.com.task.data.request.CreateTaskRequest
import br.com.task.data.request.UpdateTaskRequest
import br.com.task.data.request.toTask
import br.com.task.data.response.SimpleResponse

class TaskServiceImpl(
    private val repository: TaskRepository,
    private val validateCreateTaskRequest: ValidateCreateTaskRequest,
    private val validateUpdateTaskRequest: ValidateUpdateTaskRequest,
) : TaskService {
    override suspend fun getTasks(): List<Task> =
        repository.getTasks()

    override suspend fun getTaskById(id: String): Task? =
        repository.getTaskById(id)

    override suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse {
        val result = validateCreateTaskRequest(createTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = "Invalid Task")
        }
        val insert = repository.insert(task = createTaskRequest.toTask())
        if (!insert) {
            return SimpleResponse(success = false, message = "Cannot save invalid task", statusCode = 400)
        }
        return SimpleResponse(success = true, message = "Task created successfully", statusCode = 201)
    }

    override suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest): SimpleResponse {
        val task = getTaskById(id) ?: return SimpleResponse(
            success = false,
            message = "Task not found",
            statusCode = 404,
        )
        val result = validateUpdateTaskRequest(updateTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = "Invalid task", statusCode = 400)
        }
        return when (repository.update(id, updateTaskRequest, task)) {
            true -> SimpleResponse(success = true, message = "Task updated successfully", statusCode = 200)
            false -> SimpleResponse(success = false, message = "Cannot save task", statusCode = 400)
        }
    }

}
