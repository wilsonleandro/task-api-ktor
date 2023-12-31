package br.com.task.routes

import br.com.task.core.domain.data.service.TaskService
import br.com.task.data.request.CreateTaskRequest
import br.com.task.data.request.UpdateTaskRequest
import br.com.task.data.response.SimpleResponse
import br.com.task.utils.Constants.PARAM_ID
import br.com.task.utils.Constants.TASKS_ROUTE
import br.com.task.utils.ErrorCodes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRoute(taskService: TaskService) {
    route(TASKS_ROUTE) {
        authenticate("auth-basic") {
            getTasks(taskService)
            getTaskById(taskService)
            insertTask(taskService)
            updateTask(taskService)
            deleteTask(taskService)
            completeTask(taskService)
        }
    }
}

private fun Route.getTasks(taskService: TaskService) {
    get {
        try {
            val email = call.principal<UserIdPrincipal>()?.name
            val tasks = taskService.getTasks(email)
            call.respond(HttpStatusCode.OK, tasks)
        } catch (e: Exception) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

private fun Route.getTaskById(taskService: TaskService) {
    get("/{$PARAM_ID}") {
        val taskId = call.parameters[PARAM_ID] ?: ""
        val task = taskService.getTaskById(taskId)
        task?.let {
            call.respond(HttpStatusCode.OK, it)
        } ?: call.respond(
            HttpStatusCode.NotFound,
            SimpleResponse(
                success = false,
                message = ErrorCodes.TASK_NOT_FOUND.message,
            )
        )
    }
}

private fun Route.insertTask(taskService: TaskService) {
    post {
        val email: String? = call.principal<UserIdPrincipal>()?.name
        val request = call.receiveNullable<CreateTaskRequest>()
        request?.let { createTaskRequest ->
            val simpleResponse = taskService.insert(createTaskRequest, email)
            when {
                simpleResponse.success -> {
                    call.respond(HttpStatusCode.Created, simpleResponse)
                }

                else -> {
                    call.respond(HttpStatusCode.BadRequest, simpleResponse)
                }
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private fun Route.updateTask(taskService: TaskService) {
    put("/{$PARAM_ID}") {
        val taskId = call.parameters[PARAM_ID] ?: ""
        val request = call.receiveNullable<UpdateTaskRequest>()
        request?.let { updateTaskRequest ->
            val simpleResponse = taskService.update(taskId, updateTaskRequest)
            when {
                simpleResponse.success -> {
                    call.respond(HttpStatusCode.OK, simpleResponse)
                }

                else -> {
                    call.respond(HttpStatusCode.BadRequest, simpleResponse)
                }
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private fun Route.deleteTask(taskService: TaskService) {
    delete("/{$PARAM_ID}") {
        val taskId = call.parameters[PARAM_ID] ?: ""
        val simpleResponse = taskService.delete(taskId)
        if (simpleResponse.success) {
            call.respond(HttpStatusCode.OK, simpleResponse)
        } else {
            call.respond(HttpStatusCode.BadRequest, simpleResponse)
        }
    }
}

private fun Route.completeTask(taskService: TaskService) {
    patch("/{$PARAM_ID}") {
        val taskId = call.parameters[PARAM_ID] ?: ""
        val simpleResponse = taskService.complete(taskId)
        when {
            simpleResponse.success -> {
                call.respond(HttpStatusCode.OK, simpleResponse)
            }

            else -> {
                call.respond(HttpStatusCode.BadRequest, simpleResponse)
            }
        }
    }
}