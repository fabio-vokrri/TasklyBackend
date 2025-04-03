package it.fabiovokrri.utils

import io.ktor.server.application.*
import it.fabiovokrri.routes.databaseRoutes
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {

    val database = Database.connect(
        url = "jdbc:h2:mem:tasks;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )

    databaseRoutes()
}