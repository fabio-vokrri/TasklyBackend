package it.fabiovokrri.models

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Task(
    val id: Uuid,
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val status: TaskStatus,
)

