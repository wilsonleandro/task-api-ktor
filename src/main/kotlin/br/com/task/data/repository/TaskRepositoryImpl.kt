package br.com.task.data.repository

import br.com.task.core.domain.data.repository.TaskRepository
import br.com.task.core.domain.model.Task
import br.com.task.data.request.UpdateTaskRequest
import br.com.task.utils.Constants.TASKS_COLLECTION
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class TaskRepositoryImpl(
    database: CoroutineDatabase
) : TaskRepository {
    private val tasksCollection = database.getCollection<Task>(TASKS_COLLECTION)

    override suspend fun getTasks(customerId: String?): List<Task> {
        return tasksCollection.find(Task::customerId eq customerId).toList()
    }

    override suspend fun getTaskById(id: String): Task? =
        tasksCollection.findOneById(id)

    override suspend fun insert(task: Task): Boolean =
        tasksCollection.insertOne(task).wasAcknowledged()

    override suspend fun update(
        id: String,
        updateTaskRequest: UpdateTaskRequest,
        currentTask: Task,
    ): Boolean =
        tasksCollection.updateOneById(
            id = id,
            update = Task(
                id = currentTask.id,
                title = updateTaskRequest.title,
                description = updateTaskRequest.description,
                priority = updateTaskRequest.priority,
                dueDate = currentTask.dueDate,
                completed = currentTask.completed,
                createAt = currentTask.createAt,
            ),
        ).wasAcknowledged()

    override suspend fun delete(id: String): Boolean =
        tasksCollection.deleteOneById(id).wasAcknowledged()

    override suspend fun completedTask(id: String): Long {
        val updateResult = tasksCollection.updateOne(
            Task::id eq id, setValue(
                Task::completed,
                true,
            )
        )
        return updateResult.modifiedCount
    }

}
