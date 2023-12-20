package br.com.task.data.service

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.data.service.CustomerService
import br.com.task.core.domain.model.RegisterCustomerValidationType
import br.com.task.core.domain.usecase.ValidateRegisterCustomerUseCase
import br.com.task.data.request.AccountRequest
import br.com.task.data.request.CreateCustomerRequest
import br.com.task.data.response.SimpleResponse
import br.com.task.utils.generatedHash
import br.com.task.utils.verifyHash

class CustomerServiceImpl(
    private val repository: CustomerRepository,
    private val validateRegisterCustomerUseCase: ValidateRegisterCustomerUseCase,
) : CustomerService {

    override suspend fun login(accountRequest: AccountRequest): SimpleResponse {
        val result = verifyPasswordByEmail(accountRequest.email, accountRequest.password)
        if (!result.success) {
            return SimpleResponse(success = false, message = result.message)
        }
        return SimpleResponse(success = true, message = result.message)
    }

    override suspend fun insert(createCustomerRequest: CreateCustomerRequest): SimpleResponse {
        if (repository.checkIfCustomerExists(createCustomerRequest.email)) {
            return SimpleResponse(success = false, message = "Usuário já possui registro")
        }
        when (validateRegisterCustomerUseCase(createCustomerRequest)) {
            RegisterCustomerValidationType.NoEmail -> {
                return SimpleResponse(success = false, message = "E-mail inválido")
            }

            RegisterCustomerValidationType.EmptyField -> {
                return SimpleResponse(success = false, message = "Preencha todos os campos")
            }

            RegisterCustomerValidationType.Valid -> {
                val hashedPassword = generatedHash(createCustomerRequest.password)
                val customer = createCustomerRequest.toCustomer().copy(password = hashedPassword)
                val inserted = repository.insert(customer)
                if (!inserted) {
                    return SimpleResponse(success = false, message = "Erro ao cadastrar o usuário")
                }
                return SimpleResponse(success = true, message = "Usuário cadastrado com sucesso")
            }
        }
    }

    override suspend fun verifyPasswordByEmail(email: String, password: String): SimpleResponse {
        val actualPassword = repository.getCustomerByEmail(email)?.password
        return if (actualPassword != null) {
            val result = verifyHash(password, actualPassword)
            if (result.verified) {
                SimpleResponse(success = true, message = "Autenticado")
            } else {
                SimpleResponse(success = false, message = "E-mail ou senha inválido")
            }
        } else {
            return SimpleResponse(success = false, message = "E-mail ou senha inválido")
        }
    }
}