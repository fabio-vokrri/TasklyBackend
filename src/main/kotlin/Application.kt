package it.fabiovokrri

import io.ktor.server.application.*
import it.fabiovokrri.database.configureDatabase
import it.fabiovokrri.utils.configureContentNegotiation
import it.fabiovokrri.utils.configureDependencyInjection
import it.fabiovokrri.utils.configureSecurity

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureDependencyInjection()
    configureContentNegotiation()
    configureDatabase()
}
