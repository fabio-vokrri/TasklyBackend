package it.fabiovokrri.repositories

import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus

/**
 * Task repository
 */
interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun getById(id: Long): Task?
    suspend fun getByTitle(title: String): Task?
    suspend fun getByStatus(status: TaskStatus): List<Task>
    suspend fun getByPriority(priority: Int): List<Task>
    suspend fun getByDueDate(dueDate: Long): List<Task>

    suspend fun insert(task: Task): Boolean
    suspend fun update(task: Task): Boolean
    suspend fun delete(taskId: Long): Boolean
}