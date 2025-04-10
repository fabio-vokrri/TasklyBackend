package it.fabiovokrri.database.tables

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    val id = long("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 255).uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}