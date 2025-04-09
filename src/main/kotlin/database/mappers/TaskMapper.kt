package it.fabiovokrri.database.mappers

import it.fabiovokrri.database.tables.Tasks
import it.fabiovokrri.models.Task
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTask() = Task(
    id = this[Tasks.id],
    title = this[Tasks.title],
    description = this[Tasks.description],
    dueDate = this[Tasks.dueDate],
    priority = this[Tasks.priority],
    status = this[Tasks.status]
)