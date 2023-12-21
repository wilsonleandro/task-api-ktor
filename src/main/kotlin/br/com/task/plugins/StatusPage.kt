package br.com.task.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<TaskNotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, cause.message.toString())
        }
        exception<Throwable> { call, cause ->
            if (cause is AuthenticationException) {
                call.respond(HttpStatusCode.Unauthorized)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}

class TaskNotFoundException(message: String) : Throwable(message)
class AuthenticationException(override val message: String?) : Throwable(message)
