package br.com.task.data.service

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.data.service.TaskService
import br.com.task.core.domain.model.Task
import br.com.task.core.domain.usecase.ValidateCreateTaskRequest
import br.com.task.core.domain.usecase.ValidateUpdateTaskRequest
import br.com.task.data.request.CreateTaskRequest
import br.com.task.data.request.UpdateTaskRequest
import br.com.task.data.request.toTask
import br.com.task.data.response.SimpleResponse
import br.com.task.plugins.TaskNotFoundException
import br.com.task.utils.ErrorCodes
import br.com.task.utils.SuccessCodes

class TaskServiceImpl(
    private val repository: TaskRepository,
    private val customerRepository: CustomerRepository,
    private val validateCreateTaskRequest: ValidateCreateTaskRequest,
    private val validateUpdateTaskRequest: ValidateUpdateTaskRequest,
) : TaskService {
    override suspend fun getTasks(email: String?): List<Task> {
        email?.let {
            val customer = customerRepository.getCustomerByEmail(it)
            return repository.getTasks(customer?.id)
        } ?: return emptyList()
    }

    override suspend fun getTaskById(id: String): Task? =
        repository.getTaskById(id)

    override suspend fun insert(createTaskRequest: CreateTaskRequest, email: String?): SimpleResponse {
        val result = validateCreateTaskRequest(createTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = ErrorCodes.EMPTY_FIELDS.message)
        }
        if (email == null) {
            return SimpleResponse(success = false, message = "Usuário não encontrado")
        }
        val customer = customerRepository.getCustomerByEmail(email) ?: throw TaskNotFoundException("Usuário não encontrado")
        val insert = repository.insert(task = createTaskRequest.toTask().copy(customerId = customer.id))
        if (!insert) {
            return SimpleResponse(success = false, message = ErrorCodes.REGISTER_TASK.message)
        }
        return SimpleResponse(success = true, message = SuccessCodes.REGISTER_TASK.message)
    }

    override suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest): SimpleResponse {
        val task = getTaskById(id) ?: throw TaskNotFoundException(message = ErrorCodes.TASK_NOT_FOUND.message)
        val result = validateUpdateTaskRequest(updateTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = ErrorCodes.EMPTY_FIELDS.message)
        }
        return when (repository.update(id, updateTaskRequest, task)) {
            true -> SimpleResponse(success = true, message = SuccessCodes.UPDATE_TASK.message)
            false -> SimpleResponse(success = false, message = ErrorCodes.UPDATE_TASK.message)
        }
    }

    override suspend fun delete(id: String): SimpleResponse {
        val task = getTaskById(id)
        task?.let {
            return if (repository.delete(it.id)) {
                SimpleResponse(success = true, message = SuccessCodes.DELETE_TASK.message)
            } else {
                SimpleResponse(success = false, message = ErrorCodes.DELETE_TASK.message)
            }
        } ?: throw TaskNotFoundException(ErrorCodes.TASK_NOT_FOUND.message)
    }

    override suspend fun complete(id: String): SimpleResponse {
        val task = getTaskById(id) ?: throw TaskNotFoundException(message = ErrorCodes.TASK_NOT_FOUND.message)
        val modifiedCount = repository.completedTask(task.id)
        return if (modifiedCount > 0)
            SimpleResponse(success = true, message = SuccessCodes.COMPLETE_TASK.message)
        else
            SimpleResponse(success = true, message = ErrorCodes.COMPLETE_TASK.message)
    }

}
