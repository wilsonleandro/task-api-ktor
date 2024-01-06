package br.com.task.data.request

import br.com.task.core.domain.model.Customer
import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerRequest(
    val name: String,
    val email: String,
    val password: String,
) {

    fun toCustomer(): Customer {
        return Customer(
            name = this.name,
            email = this.email,
            password = this.password
        )
    }

}
