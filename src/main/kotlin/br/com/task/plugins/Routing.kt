package br.com.task.plugins

import br.com.task.core.domain.data.service.CustomerService
import br.com.task.core.domain.data.service.TaskService
import br.com.task.routes.customerRoute
import br.com.task.routes.taskRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val taskService by inject<TaskService>()
    val customerService by inject<CustomerService>()

    install(Routing) {
        taskRoute(taskService)
        customerRoute(customerService)
    }
}
