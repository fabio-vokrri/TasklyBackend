package it.fabiovokrri.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.fabiovokrri.routes.authRoutes
import it.fabiovokrri.routes.taskRoutes
import it.fabiovokrri.routes.usersRoutes


fun Application.configureRoutes() {
    routing {
        // authentication endpoints
        authRoutes()
        // tasks endpoints
        taskRoutes()
        // users endpoints
        usersRoutes()

        get("/health") {
            call.respond(HttpStatusCode.OK, "Server up and running...")
        }
    }
}