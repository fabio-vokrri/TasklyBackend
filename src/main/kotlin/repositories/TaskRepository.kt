package it.fabiovokrri.repositories

import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus

/**
 * Task repository
 */
interface TaskRepository {
    suspend fun getAllTasksOf(userId: Long): List<Task>
    suspend fun getById(id: Long, userId: Long): Task?
    suspend fun getByTitle(title: String, userId: Long): List<Task>
    suspend fun getByStatus(status: TaskStatus, userId: Long): List<Task>
    suspend fun getByPriority(priority: Int, userId: Long): List<Task>
    suspend fun getByDueDate(dueDate: Long, userId: Long): List<Task>

    suspend fun insert(task: Task, userId: Long): Boolean
    suspend fun update(task: Task, userId: Long): Boolean
    suspend fun delete(taskId: Long, userId: Long): Boolean
}