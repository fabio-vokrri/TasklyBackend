package it.fabiovokrri.database.mappers

import it.fabiovokrri.database.tables.Users
import it.fabiovokrri.models.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(
    withPassword: Boolean = false
) = User(
    id = this[Users.id],
    email = this[Users.email],
    name = this[Users.name],
    password = if (withPassword) this[Users.password] else null,
)