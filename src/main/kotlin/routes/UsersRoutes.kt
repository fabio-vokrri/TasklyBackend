package it.fabiovokrri.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.fabiovokrri.models.User
import it.fabiovokrri.services.UserService
import org.koin.ktor.ext.inject

fun Application.usersRoutes() {
    val userService by inject<UserService>()

    routing {
        route("/v1/users") {
            get {
                if (call.queryParameters.isEmpty()) return@get call.respond(HttpStatusCode.BadRequest)
                handleQueries(userService)
            }

            post {
                val user = call.receive<User>()
                val inserted = userService.insert(user)

                if (inserted) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotModified)

            }

            put {
                val user = call.receive<User>()
                val updated = userService.update(user)

                if (updated) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotModified)
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = userService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, user)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val deleted = userService.delete(id)

                if (deleted) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

/**
 * Handles the query parameters in the given [RoutingContext] via the [userService].
 * Handles one query parameter only: if multiple are passed, only the first one will be processed.
 */
private suspend fun RoutingContext.handleQueries(userService: UserService): Unit = with(this.call) {
    request.queryParameters["userName"]?.let { userName ->
        val user = userService.getByUsername(userName) ?: return respond(HttpStatusCode.NotFound)
        return respond(HttpStatusCode.OK, user)
    }

    request.queryParameters["email"]?.let { email ->
        val user = userService.getByEmail(email) ?: return respond(HttpStatusCode.NotFound)
        return respond(HttpStatusCode.OK, user)
    }
}