package it.fabiovokrri.repositories

import it.fabiovokrri.models.User

/**
 * User repository
 */
interface UserRepository {
    suspend fun getById(id: Long): User?
    suspend fun getByEmail(email: String): User?
    suspend fun getByUsername(username: String): User?

    suspend fun insert(user: User): Boolean
    suspend fun update(user: User): Boolean
    suspend fun delete(id: Long): Boolean

    suspend fun validateCredentials(username: String, password: String): User?
}