package br.com.task.core.domain.data.service

import br.com.task.data.request.AccountRequest
import br.com.task.data.request.CreateCustomerRequest
import br.com.task.data.response.SimpleResponse

interface CustomerService {
    suspend fun login(accountRequest: AccountRequest): SimpleResponse
    suspend fun insert(createCustomerRequest: CreateCustomerRequest): SimpleResponse
    suspend fun verifyPasswordByEmail(email: String, password: String): SimpleResponse
}