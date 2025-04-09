package routes

import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class UsersRoutesTest {

    @Test
    fun testGetV1Users() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/v1/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostV1Users() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.post("/v1/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutV1Users() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.put("/v1/users").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteV1UsersId() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.delete("/v1/users/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetV1UsersId() = testApplication {
        application {
            TODO("Add the Ktor module for the test")
        }
        client.get("/v1/users/{id}").apply {
            TODO("Please write your test here")
        }
    }
}