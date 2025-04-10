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

@Single
class OnlineTaskRepository : TaskRepository {

    override suspend fun getAllTasksOf(userId: Long): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .map { it.toTask() }
    }


    override suspend fun getByTitle(title: String, userId: Long): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .where { Tasks.title eq title }
            .map { it.toTask() }
    }

    override suspend fun getById(id: Long, userId: Long): Task? = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .where { Tasks.id eq id }
            .singleOrNull()
            ?.toTask()
    }

    override suspend fun getByStatus(status: TaskStatus, userId: Long): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .where { Tasks.status eq status }
            .map { it.toTask() }
    }

    override suspend fun getByPriority(priority: Int, userId: Long): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .where { Tasks.priority eq priority }
            .map { it.toTask() }
    }

    override suspend fun getByDueDate(dueDate: Long, userId: Long): List<Task> = query {
        (UserTasksCrossRef innerJoin Tasks)
            .selectAll()
            .where { UserTasksCrossRef.userId eq userId }
            .where { Tasks.dueDate eq dueDate }
            .map { it.toTask() }
    }

    override suspend fun insert(task: Task, userId: Long): Boolean = query {
        val updated1 = Tasks.insert {
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        }

        val updated2 = UserTasksCrossRef.insert {
            it[UserTasksCrossRef.userId] = userId
            it[UserTasksCrossRef.taskId] = updated1[Tasks.id]
        }

        updated1.insertedCount == 1 && updated2.insertedCount == 1
    }

    override suspend fun update(task: Task, userId: Long): Boolean = query {
        val updatedRows = Tasks.update(
            where = { Tasks.id eq task.id },
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

    override suspend fun delete(taskId: Long, userId: Long): Boolean = query {
        val updatedRows = Tasks.deleteWhere(limit = 1) { id eq taskId }
        updatedRows == 1
    }
}