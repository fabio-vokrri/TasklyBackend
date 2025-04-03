package it.fabiovokrri.database.tables

import it.fabiovokrri.models.TaskStatus
import org.jetbrains.exposed.sql.Table

object Tasks : Table("tasks") {
    val id = long("id").autoIncrement()
    val title = varchar("name", 255)
    val description = text("description")
    val dueDate = long("dueDate")
    val priority = integer("priority")
    val status = enumeration("status", TaskStatus::class)

    override val primaryKey = PrimaryKey(id)
}