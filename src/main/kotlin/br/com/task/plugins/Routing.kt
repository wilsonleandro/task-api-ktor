package br.com.task.plugins

import br.com.task.core.domain.data.service.TaskService
import br.com.task.routes.taskRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val taskService by inject<TaskService>()
    install(Routing) {
        taskRoute(taskService)
    }
}
