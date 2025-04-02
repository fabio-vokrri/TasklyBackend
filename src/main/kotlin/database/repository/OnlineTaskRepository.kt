package it.fabiovokrri.database.repository

import it.fabiovokrri.database.model.Task
import it.fabiovokrri.database.model.TaskStatus
import it.fabiovokrri.database.model.TaskTable
import it.fabiovokrri.database.util.dbQuery
import it.fabiovokrri.database.util.toTask
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OnlineTaskRepository(private val database: Database) : TaskRepository {

    init {
        transaction(database) {
            SchemaUtils.create(TaskTable)
        }
    }

    override suspend fun getAllTasks(): List<Task> = dbQuery {
        TaskTable.selectAll().map { it.toTask() }
    }


    override suspend fun getByTitle(title: String): Task? = dbQuery {
        TaskTable.selectAll()
            .where { TaskTable.title eq title }
            .singleOrNull()
            ?.toTask()

    }

    override suspend fun getById(id: Long): Task? = dbQuery {
        TaskTable.selectAll()
            .where { TaskTable.id eq id }
            .singleOrNull()
            ?.toTask()
    }

    override suspend fun getByStatus(status: TaskStatus): List<Task> = dbQuery {
        TaskTable.selectAll()
            .where { TaskTable.status eq status }
            .map { it.toTask() }
    }

    override suspend fun getByPriority(priority: Int): List<Task> = dbQuery {
        TaskTable.selectAll()
            .where { TaskTable.priority eq priority }
            .map { it.toTask() }
    }

    override suspend fun getByDueDate(dueDate: Long): List<Task> = dbQuery {
        TaskTable.selectAll()
            .where { TaskTable.dueDate eq dueDate }
            .map { it.toTask() }
    }

    override suspend fun insert(task: Task): Boolean = dbQuery {
        val updatedRows = TaskTable.insert {
            it[id] = task.id
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        }

        updatedRows.insertedCount > 0
    }

    override suspend fun update(task: Task): Boolean = dbQuery {
        val updatedRows = TaskTable.update(
            where = { TaskTable.id eq task.id },
            limit = 1,
        ) {
            it[title] = task.title
            it[description] = task.description
            it[dueDate] = task.dueDate
            it[priority] = task.priority
            it[status] = task.status
        }

        updatedRows > 0
    }

    override suspend fun delete(taskId: Long): Boolean = dbQuery {
        val updatedRows = TaskTable.deleteWhere { TaskTable.id eq taskId }
        updatedRows > 0
    }
}