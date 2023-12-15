package br.com.task.routes

import br.com.task.core.domain.data.service.TaskService
import br.com.task.utils.Constants.TASKS_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRoute(taskService: TaskService) {
    route(TASKS_ROUTE) {
        getTasks(taskService)
    }
}

private fun Route.getTasks(taskService: TaskService) {
    get {
        try {
            val tasks = taskService.getTasks()
            call.respond(HttpStatusCode.OK, tasks)
        } catch (e: Exception) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}