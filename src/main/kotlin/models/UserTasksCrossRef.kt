package it.fabiovokrri.models

import kotlinx.serialization.Serializable

@Serializable
data class UserTasksCrossRef(
    val userId: Long,
    val taskId: Long,
)