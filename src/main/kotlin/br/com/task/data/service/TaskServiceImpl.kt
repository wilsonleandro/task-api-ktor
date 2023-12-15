package br.com.task.data.service

import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.data.service.TaskService
import br.com.task.core.domain.model.Task

class TaskServiceImpl(
    private val repository: TaskRepository
): TaskService {
    override suspend fun getTasks(): List<Task> =
        repository.getTasks()
}