package it.fabiovokrri.services

import it.fabiovokrri.models.User
import it.fabiovokrri.repositories.UserRepository
import org.koin.core.annotation.Single

@Single
class UserService(private val userRepository: UserRepository) {
    suspend fun getById(id: Long): User? = userRepository.getById(id)
    suspend fun getByUsername(username: String): User? = userRepository.getByUsername(username)
    suspend fun getByEmail(email: String): User? = userRepository.getByEmail(email)

    suspend fun insert(user: User): Boolean = userRepository.insert(user)
    suspend fun update(user: User): Boolean = userRepository.update(user)
    suspend fun delete(id: Long): Boolean = userRepository.delete(id)

    suspend fun validateCredentials(username: String, password: String): User? =
        userRepository.validateCredentials(username, password)
}