package it.fabiovokrri.models

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class UserTasksCrossRef(
    val userId: Uuid,
    val taskId: Uuid,
)