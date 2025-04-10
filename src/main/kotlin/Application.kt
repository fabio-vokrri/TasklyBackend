package it.fabiovokrri

import io.ktor.server.application.*
import io.ktor.server.netty.*
import it.fabiovokrri.database.Database
import it.fabiovokrri.utils.configureContentNegotiation
import it.fabiovokrri.utils.configureDependencyInjection
import it.fabiovokrri.utils.configureRoutes
import it.fabiovokrri.utils.configureSecurity

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    Database.init()

    configureSecurity()
    configureDependencyInjection()
    configureContentNegotiation()

    configureRoutes()
}
