package br.com.task.data.service

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.data.service.CustomerService
import br.com.task.core.domain.model.RegisterCustomerValidationType
import br.com.task.core.domain.usecase.ValidateRegisterCustomerUseCase
import br.com.task.data.request.AccountRequest
import br.com.task.data.request.CreateCustomerRequest
import br.com.task.data.response.SimpleResponse
import br.com.task.utils.ErrorCodes
import br.com.task.utils.SuccessCodes
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
            return SimpleResponse(success = false, message = ErrorCodes.CUSTOMER_EXISTS.message)
        }
        when (validateRegisterCustomerUseCase(createCustomerRequest)) {
            RegisterCustomerValidationType.NoEmail -> {
                return SimpleResponse(success = false, message = ErrorCodes.CUSTOMER_NO_EMAIL.message)
            }

            RegisterCustomerValidationType.EmptyField -> {
                return SimpleResponse(success = false, message = ErrorCodes.EMPTY_FIELDS.message)
            }

            RegisterCustomerValidationType.Valid -> {
                val hashedPassword = generatedHash(createCustomerRequest.password)
                val customer = createCustomerRequest.toCustomer().copy(password = hashedPassword)
                val inserted = repository.insert(customer)
                if (!inserted) {
                    return SimpleResponse(success = false, message = ErrorCodes.CUSTOMER_REGISTER.message)
                }
                return SimpleResponse(success = true, message = SuccessCodes.CUSTOMER_CREATED.message)
            }
        }
    }

    override suspend fun verifyPasswordByEmail(email: String, password: String): SimpleResponse {
        val actualPassword = repository.getCustomerByEmail(email)?.password
        return if (actualPassword != null) {
            val result = verifyHash(password, actualPassword)
            if (result.verified) {
                SimpleResponse(success = true, message = SuccessCodes.CUSTOMER_AUTHENTICATED.message)
            } else {
                SimpleResponse(success = false, message = ErrorCodes.CUSTOMER_NOT_FOUNT.message)
            }
        } else {
            return SimpleResponse(success = false, message = ErrorCodes.CUSTOMER_CREDENTIALS_ERROR.message)
        }
    }
}
