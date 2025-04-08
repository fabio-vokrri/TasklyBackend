package it.fabiovokrri.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val status: TaskStatus,
)

