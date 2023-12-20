package br.com.task.routes

import br.com.task.core.domain.data.service.CustomerService
import br.com.task.data.request.AccountRequest
import br.com.task.data.request.CreateCustomerRequest
import br.com.task.utils.Constants.CUSTOMER_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRoute(service: CustomerService) {
    route(CUSTOMER_ROUTE) {
        insert(service)
        login(service)
    }
}

private fun Route.insert(service: CustomerService) {
    post {
        val request = call.receiveNullable<CreateCustomerRequest>()
        request?.let { createCustomerRequest ->
            val simpleResponse = service.insert(createCustomerRequest)
            if (simpleResponse.success) {
                call.respond(HttpStatusCode.Created, simpleResponse)
            } else {
                call.respond(HttpStatusCode.BadRequest, simpleResponse)
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private fun Route.login(service: CustomerService) {
    post("/login") {
        val request = call.receiveNullable<AccountRequest>()
        request?.let { accountRequest ->
            val simpleResponse = service.login(accountRequest)
            if (simpleResponse.success) {
                call.respond(HttpStatusCode.OK, simpleResponse)
            } else {
                call.respond(HttpStatusCode.BadRequest, simpleResponse)
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}
