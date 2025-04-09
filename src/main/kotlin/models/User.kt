package it.fabiovokrri.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = 0L,
    val name: String,
    val email: String,
    val password: String? = null,
)