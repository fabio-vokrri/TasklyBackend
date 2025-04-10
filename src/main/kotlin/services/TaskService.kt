package it.fabiovokrri.services

import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus
import it.fabiovokrri.repositories.TaskRepository
import org.koin.core.annotation.Single

@Single
class TaskService(private val repository: TaskRepository) {
    suspend fun getAllTasksOf(userId: Long): List<Task> = repository.getAllTasksOf(userId)
    suspend fun getById(id: Long, userId: Long): Task? = repository.getById(id, userId)
    suspend fun getByTitle(title: String, userId: Long): List<Task> = repository.getByTitle(title, userId)
    suspend fun getByStatus(status: TaskStatus, userId: Long): List<Task> = repository.getByStatus(status, userId)
    suspend fun getByPriority(priority: Int, userId: Long): List<Task> = repository.getByPriority(priority, userId)
    suspend fun getByDueDate(dueDate: Long, userId: Long): List<Task> = repository.getByDueDate(dueDate, userId)

    suspend fun insert(task: Task, userId: Long): Boolean = repository.insert(task, userId)
    suspend fun update(task: Task, userId: Long): Boolean = repository.update(task, userId)
    suspend fun delete(taskId: Long, userId: Long): Boolean = repository.delete(taskId, userId)
}