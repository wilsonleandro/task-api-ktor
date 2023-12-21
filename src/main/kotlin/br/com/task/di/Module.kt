package br.com.task.di

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.data.service.CustomerService
import br.com.task.core.domain.data.service.TaskService
import br.com.task.core.domain.usecase.*
import br.com.task.data.repository.CustomerRepositoryImpl
import br.com.task.data.repository.TaskRepositoryImpl
import br.com.task.data.service.CustomerServiceImpl
import br.com.task.data.service.TaskServiceImpl
import br.com.task.utils.Constants.LOCAL_DATABASE_NAME
import br.com.task.utils.Constants.MONGODB_URI_LOCAL
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseModule = module {
    single {
        val client = KMongo.createClient(connectionString = MONGODB_URI_LOCAL).coroutine
        client.getDatabase(LOCAL_DATABASE_NAME)
    }
}

val repositoryModule = module {
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
    single<CustomerRepository> {
        CustomerRepositoryImpl(get())
    }
}

val serviceModule = module {
    single<TaskService> {
        TaskServiceImpl(get(), get(), get(), get())
    }
    single<CustomerService> {
        CustomerServiceImpl(get(), get())
    }
}

val usecaseModule = module {
    single<ValidateCreateTaskRequest> {
        ValidateCreateTaskRequestImpl()
    }
    single<ValidateUpdateTaskRequest> {
        ValidateUpdateTaskRequestImpl()
    }
    single<ValidateRegisterCustomerUseCase> {
        ValidateRegisterCustomerUseCaseImpl()
    }
}
