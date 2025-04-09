package it.fabiovokrri.models

import kotlinx.serialization.Serializable

@Serializable
data class TasksUserCrossRef(
    val userId: Long,
    val taskId: Long,
)