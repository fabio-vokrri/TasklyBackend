package it.fabiovokrri.database

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import it.fabiovokrri.database.model.Task
import it.fabiovokrri.database.model.TaskStatus
import it.fabiovokrri.database.repository.OnlineTaskRepository
import it.fabiovokrri.service.TaskService
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {

    val database = Database.connect(
        url = "jdbc:h2:mem:tasks;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )

    val taskRepository = OnlineTaskRepository(database)
    val taskService = TaskService(taskRepository)

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

            get("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val task = taskService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, task)
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toLong() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val task = call.receive<Task>()
                taskService.update(task)

                call.respond(HttpStatusCode.OK)
            }

            get("/{title}") {
                val title = call.parameters["title"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val task = taskService.getByTitle(title) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(HttpStatusCode.OK, task)
            }

            get("/{status}") {
                val status = call.parameters["status"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val tasks = try {
                    taskRepository.getByStatus(TaskStatus.valueOf(status))
                } catch (exception: IllegalArgumentException) {
                    return@get call.respond(HttpStatusCode.BadRequest)
                }

                call.respond(HttpStatusCode.OK, tasks)
            }

            get("/{priority}") {
                val priority =
                    call.parameters["priority"]?.toInt() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val tasks = taskRepository.getByPriority(priority)

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