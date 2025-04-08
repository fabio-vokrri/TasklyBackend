package it.fabiovokrri.database.utils

import it.fabiovokrri.database.tables.Tasks
import it.fabiovokrri.database.tables.Users
import it.fabiovokrri.models.Task
import it.fabiovokrri.models.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTask() = Task(
    id = this[Tasks.id],
    title = this[Tasks.title],
    description = this[Tasks.description],
    dueDate = this[Tasks.dueDate],
    priority = this[Tasks.priority],
    status = this[Tasks.status]
)

fun ResultRow.toUser() = User(
    id = this[Users.id],
    email = this[Users.email],
    name = this[Users.name],
    password = this[Users.password],
)