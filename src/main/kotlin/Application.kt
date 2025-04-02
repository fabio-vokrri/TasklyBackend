package it.fabiovokrri

import io.ktor.server.application.*
import it.fabiovokrri.database.configureDatabase

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // configureSecurity()
    configureContentNegotiation()
    configureDatabase()
}
