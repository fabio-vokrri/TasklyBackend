package it.fabiovokrri.repositories

import it.fabiovokrri.database.Database.query
import it.fabiovokrri.database.mappers.toTask
import it.fabiovokrri.database.tables.Tasks
import it.fabiovokrri.database.tables.UserTasksCrossRef
import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
@Single
class OnlineTaskRepository : TaskRepository {

    override suspend fun getAllTasksOf(userId: Uuid): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .map { it.toTask() }
    }


    override suspend fun getByTitle(title: String, userId: Uuid): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .where { Tasks.title eq title }
            .map { it.toTask() }
    }

    override suspend fun getById(id: Uuid, userId: Uuid): Task? = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .where { Tasks.id eq id.toJavaUuid() }
            .singleOrNull()
            ?.toTask()
    }

    override suspend fun getByStatus(status: TaskStatus, userId: Uuid): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .where { Tasks.status eq status }
            .map { it.toTask() }
    }

    override suspend fun getByPriority(priority: Int, userId: Uuid): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .where { Tasks.priority eq priority }
            .map { it.toTask() }
    }

    override suspend fun getByDueDate(dueDate: Long, userId: Uuid): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId.toJavaUuid() }
            .where { Tasks.dueDate eq dueDate }
            .map { it.toTask() }
    }

    override suspend fun insert(task: Task, userId: Uuid): Boolean = query {
        val taskId = Tasks.insert {
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        } get Tasks.id

        val updated = UserTasksCrossRef.insert {
            it[UserTasksCrossRef.userId] = userId.toJavaUuid()
            it[UserTasksCrossRef.taskId] = taskId
        }

        updated.insertedCount == 1
    }

    override suspend fun update(task: Task, userId: Uuid): Boolean = query {
        val updatedRows = Tasks.update(
            where = { Tasks.id eq task.id.toJavaUuid() },
            limit = 1,
        ) {
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        }

        updatedRows == 1
    }

    override suspend fun delete(taskId: Uuid, userId: Uuid): Boolean = query {
        val updatedRows = Tasks.deleteWhere(limit = 1) { id eq taskId.toJavaUuid() }
        updatedRows == 1
    }
}