package it.fabiovokrri.database.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UserTasksCrossRef : Table("user_tasks_crossref") {
    val userId = reference("user_id", Users.id, onDelete = ReferenceOption.CASCADE)
    val taskId = reference("task_id", Tasks.id)

    override val primaryKey = PrimaryKey(userId, taskId)
}
