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

fun Application.taskRoutes() {
    val taskService by inject<TaskService>()

    routing {
        route("/v1/tasks") {
            get {
                // no filters
                if (call.queryParameters.isEmpty()) {
                    val tasks = taskService.getAllTasks()
                    call.respond(HttpStatusCode.OK, tasks)

                    return@get
                }

                // handles optional filtering
                handleQueries(taskService)
            }

            post {
                val task = call.receive<Task>()
                val inserted = taskService.insert(task)

                if (inserted) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotModified)
            }

            put {
                val task = call.receive<Task>()
                val updated = taskService.update(task)

                if (updated) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotModified)
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val task = taskService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, task)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val deleted = taskService.delete(id)

                if (deleted) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

/**
 * Handles the query parameters in the given [RoutingContext] via the [taskService].
 * Handles one query parameter only: if multiple are passed, only the first one will be processed.
 */
private suspend fun RoutingContext.handleQueries(taskService: TaskService): Unit = with(this.call) {
    // queries tasks by the given title
    request.queryParameters["title"]?.let { title ->
        val tasks = taskService.getByTitle(title)
        return respond(HttpStatusCode.OK, tasks)
    }

    // queries tasks by the given status
    request.queryParameters["status"]?.let { status ->
        return try {
            val tasks = taskService.getByStatus(TaskStatus.valueOf(status))
            respond(HttpStatusCode.OK, tasks)
        } catch (exception: IllegalArgumentException) {
            respond(HttpStatusCode.BadRequest, exception)
        }

    }

    // queries tasks by the given priority
    request.queryParameters["priority"]?.let { priority ->
        return try {
            val tasks = taskService.getByPriority(priority.toInt())
            respond(HttpStatusCode.OK, tasks)
        } catch (exception: NumberFormatException) {
            respond(HttpStatusCode.BadRequest, exception)
        }
    }

    // queries tasks by the given dueDate
    request.queryParameters["dueDate"]?.let { dueDate ->
        return try {
            val tasks = taskService.getByDueDate(dueDate.toLong())
            respond(HttpStatusCode.OK, tasks)
        } catch (exception: NumberFormatException) {
            respond(HttpStatusCode.BadRequest, exception)
        }
    }
}
