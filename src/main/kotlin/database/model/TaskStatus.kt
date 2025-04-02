package it.fabiovokrri.database.model

import kotlinx.serialization.Serializable

@Serializable
enum class TaskStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
}