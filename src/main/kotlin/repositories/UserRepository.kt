package it.fabiovokrri.repositories

import it.fabiovokrri.models.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * User repository
 */
@OptIn(ExperimentalUuidApi::class)
interface UserRepository {
    suspend fun getById(id: Uuid): User?
    suspend fun getByEmail(email: String): User?
    suspend fun getByUsername(username: String): User?

    suspend fun insert(user: User): Boolean
    suspend fun update(user: User): Boolean
    suspend fun delete(id: Uuid): Boolean

    suspend fun validateCredentials(username: String, password: String): User?
}