package it.fabiovokrri.models

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class User(
    val id: Uuid,
    val name: String,
    val email: String,
    val password: String? = null,
)