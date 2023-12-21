package br.com.task.core.domain.data.repository

import br.com.task.core.domain.model.Task
import br.com.task.data.request.UpdateTaskRequest

interface TaskRepository {
    suspend fun getTasks(customerId: String?): List<Task>
    suspend fun getTaskById(id: String): Task?
    suspend fun insert(task: Task): Boolean
    suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest, currentTask: Task): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun completedTask(id: String): Long
}
