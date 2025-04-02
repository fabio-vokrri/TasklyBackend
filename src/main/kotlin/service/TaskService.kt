package it.fabiovokrri.service

import it.fabiovokrri.database.model.Task
import it.fabiovokrri.database.model.TaskStatus
import it.fabiovokrri.database.repository.TaskRepository

class TaskService(private val repository: TaskRepository) {
    suspend fun getAllTasks(): List<Task> = repository.getAllTasks()
    suspend fun getById(id: Long): Task? = repository.getById(id)
    suspend fun getByTitle(title: String): Task? = repository.getByTitle(title)
    suspend fun getByStatus(status: TaskStatus): List<Task> = repository.getByStatus(status)
    suspend fun getByPriority(priority: Int): List<Task> = repository.getByPriority(priority)
    suspend fun getByDueDate(dueDate: Long): List<Task> = repository.getByDueDate(dueDate)

    suspend fun insert(task: Task): Boolean = repository.insert(task)
    suspend fun update(task: Task): Boolean = repository.update(task)
    suspend fun delete(taskId: Long): Boolean = repository.delete(taskId)
}