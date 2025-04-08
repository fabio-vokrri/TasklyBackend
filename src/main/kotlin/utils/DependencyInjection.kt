package it.fabiovokrri.utils

import io.ktor.server.application.*
import it.fabiovokrri.di.TasksModule
import it.fabiovokrri.di.UsersModule
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(TasksModule().module)
        modules(UsersModule().module)
    }
}