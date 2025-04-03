package it.fabiovokrri.database.tables

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}