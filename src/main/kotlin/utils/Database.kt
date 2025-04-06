package it.fabiovokrri.utils

import io.ktor.server.application.*
import it.fabiovokrri.database.tables.Tasks
import it.fabiovokrri.database.tables.Users
import it.fabiovokrri.routes.databaseRoutes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {

    Database.connect(
        url = "jdbc:h2:mem:tasks;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )

    transaction {
        SchemaUtils.create(Tasks)
        SchemaUtils.create(Users)
    }

    databaseRoutes()
}