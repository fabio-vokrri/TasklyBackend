package it.fabiovokrri.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.fabiovokrri.models.AuthRequest
import it.fabiovokrri.models.AuthResponse
import it.fabiovokrri.models.User
import it.fabiovokrri.services.JWTService
import it.fabiovokrri.services.UserService
import org.koin.ktor.ext.inject

fun Application.authRoutes() {
    val userService by inject<UserService>()
    val jwtService by inject<JWTService>()

    routing {
        post("/register") {
            val user = call.receive<User>()

            if (user.name.isBlank() || user.email.isBlank()) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val inserted = userService.insert(user)
            if (!inserted) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            val token = jwtService.generateToken(user)
            call.respond(AuthResponse(token, user))
        }

        post("/login") {
            val request = call.receive<AuthRequest>()
            
        }
    }
}