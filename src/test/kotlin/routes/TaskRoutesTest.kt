package routes

import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class TaskRoutesTest {

    @Test
    fun testGetV1Tasks() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/v1/tasks").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostV1Tasks() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.post("/v1/tasks").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutV1Tasks() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.put("/v1/tasks").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteV1TasksId() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.delete("/v1/tasks/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetV1TasksId() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/v1/tasks/{id}").apply {
            TODO("Please write your test here")
        }
    }
}