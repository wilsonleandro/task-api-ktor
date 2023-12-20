package br.com.task.plugins

import br.com.task.core.domain.data.service.CustomerService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject


fun Application.configureSecurity() {
    val service by inject<CustomerService>()

    authentication {
        basic(name = "auth-basic") {
            realm = "Ktor Server"
            validate { credentials ->
                val email = credentials.name
                val password = credentials.password
                val result = service.verifyPasswordByEmail(email, password)
                if (result.success) {
                    UserIdPrincipal(email)
                } else {
                    null
                }
            }
        }
    }
}
