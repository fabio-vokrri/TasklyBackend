package it.fabiovokrri.database.util

import it.fabiovokrri.database.model.Task
import it.fabiovokrri.database.model.TaskTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTask() = Task(
    id = this[TaskTable.id],
    title = this[TaskTable.title],
    description = this[TaskTable.description],
    dueDate = this[TaskTable.dueDate],
    priority = this[TaskTable.priority],
    status = this[TaskTable.status]
)