package it.fabiovokrri.repositories

import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Task repository
 */
@OptIn(ExperimentalUuidApi::class)
interface TaskRepository {
    suspend fun getAllTasksOf(userId: Uuid): List<Task>
    suspend fun getById(id: Uuid, userId: Uuid): Task?
    suspend fun getByTitle(title: String, userId: Uuid): List<Task>
    suspend fun getByStatus(status: TaskStatus, userId: Uuid): List<Task>
    suspend fun getByPriority(priority: Int, userId: Uuid): List<Task>
    suspend fun getByDueDate(dueDate: Long, userId: Uuid): List<Task>

    suspend fun insert(task: Task, userId: Uuid): Boolean
    suspend fun update(task: Task, userId: Uuid): Boolean
    suspend fun delete(taskId: Uuid, userId: Uuid): Boolean
}