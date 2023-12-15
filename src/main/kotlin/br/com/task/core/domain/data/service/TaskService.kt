package br.com.task.core.domain.data.service

import br.com.task.core.domain.model.Task

interface TaskService {
    suspend fun getTasks(): List<Task>
}