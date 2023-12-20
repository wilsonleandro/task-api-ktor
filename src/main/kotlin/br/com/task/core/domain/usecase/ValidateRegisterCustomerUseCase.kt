package br.com.task.core.domain.usecase

import br.com.task.core.domain.model.RegisterCustomerValidationType
import br.com.task.data.request.CreateCustomerRequest

interface ValidateRegisterCustomerUseCase {
    operator fun invoke(createCustomerRequest: CreateCustomerRequest): RegisterCustomerValidationType
}

class ValidateRegisterCustomerUseCaseImpl : ValidateRegisterCustomerUseCase {

    override fun invoke(createCustomerRequest: CreateCustomerRequest): RegisterCustomerValidationType {
        if (createCustomerRequest.email.isEmpty() ||
            createCustomerRequest.name.isEmpty() ||
            createCustomerRequest.password.isEmpty()
        )
            return RegisterCustomerValidationType.EmptyField
        if (!createCustomerRequest.email.matches(EMAIL_REGEX))
            return RegisterCustomerValidationType.NoEmail
        return RegisterCustomerValidationType.Valid
    }

    companion object {
        private const val PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
                "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
        private val EMAIL_REGEX = Regex(PATTERN)
    }

}
