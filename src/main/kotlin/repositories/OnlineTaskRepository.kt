package it.fabiovokrri.repositories

import it.fabiovokrri.database.Database.dbQuery
import it.fabiovokrri.database.mappers.toTask
import it.fabiovokrri.database.tables.Tasks
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

    override suspend fun getAllTasks(): List<Task> = dbQuery {
        Tasks.selectAll().map { it.toTask() }
    }


    override suspend fun getByTitle(title: String): List<Task> = dbQuery {
        Tasks.selectAll()
            .where { Tasks.title eq title }
            .map { it.toTask() }
    }

    override suspend fun getById(id: Long): Task? = dbQuery {
        Tasks.selectAll()
            .where { Tasks.id eq id }
            .singleOrNull()
            ?.toTask()
    }

    override suspend fun getByStatus(status: TaskStatus): List<Task> = dbQuery {
        Tasks.selectAll()
            .where { Tasks.status eq status }
            .map { it.toTask() }
    }

    override suspend fun getByPriority(priority: Int): List<Task> = dbQuery {
        Tasks.selectAll()
            .where { Tasks.priority eq priority }
            .map { it.toTask() }
    }

    override suspend fun getByDueDate(dueDate: Long): List<Task> = dbQuery {
        Tasks.selectAll()
            .where { Tasks.dueDate eq dueDate }
            .map { it.toTask() }
    }

    override suspend fun insert(task: Task): Boolean = dbQuery {
        val updatedRows = Tasks.insert {
            it[id] = task.id
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        }

        updatedRows.insertedCount == 1
    }

    override suspend fun update(task: Task): Boolean = dbQuery {
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

    override suspend fun delete(taskId: Long): Boolean = dbQuery {
        val updatedRows = Tasks.deleteWhere(limit = 1) { id eq taskId }
        updatedRows == 1
    }
}