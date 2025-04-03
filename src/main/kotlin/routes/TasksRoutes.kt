package it.fabiovokrri.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.fabiovokrri.models.Task
import it.fabiovokrri.models.TaskStatus
import it.fabiovokrri.services.TaskService
import org.koin.ktor.ext.inject

fun Application.databaseRoutes() {
    val taskService by inject<TaskService>()

    routing {
        route("/tasks") {
            get {
                val tasks = taskService.getAllTasks()
                call.respond(HttpStatusCode.OK, tasks)
            }

            post {
                val task = call.receive<Task>()
                val id = taskService.insert(task)
                call.respond(HttpStatusCode.OK, id)
            }

            put {
                val task = call.receive<Task>()
                taskService.update(task)

                call.respond(HttpStatusCode.OK)
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val task = taskService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, task)
            }

            get("/{title}") {
                val title = call.parameters["title"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val task = taskService.getByTitle(title) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, task)
            }

            get("/{status}") {
                val status = call.parameters["status"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val tasks = try {
                    taskService.getByStatus(TaskStatus.valueOf(status))
                } catch (exception: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.BadRequest)
                }

                call.respond(HttpStatusCode.OK, tasks)
            }

            get("/{priority}") {
                val priority =
                    call.parameters["priority"]?.toInt() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val tasks = taskService.getByPriority(priority)

                call.respond(HttpStatusCode.OK, tasks)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val result = taskService.delete(id)

                if (result) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.ExpectationFailed)
            }
        }
    }
}
