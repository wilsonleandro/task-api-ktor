package br.com.task.core.domain.data.repository

import br.com.task.core.domain.model.Customer

interface CustomerRepository {
    suspend fun insert(customer: Customer): Boolean
    suspend fun checkIfCustomerExists(email: String): Boolean
    suspend fun getCustomerByEmail(email: String): Customer?
}
