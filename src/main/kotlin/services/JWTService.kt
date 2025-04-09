package it.fabiovokrri.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import it.fabiovokrri.models.User
import org.koin.core.annotation.Single
import java.util.*

@Single
class JWTService {
    fun generateToken(user: User): String = JWT.create()
        .withSubject(SUBJECT)
        .withIssuer(ISSUER)
        .withClaim("id", user.id)
        .withClaim("username", user.name)
        .withExpiresAt(getExpirationDate())
        .sign(ALGORITHM)

    private fun getExpirationDate(): Date = Date(System.currentTimeMillis() + VALIDITY_MILLIS)

    companion object {
        private const val ISSUER = "Taskly App"
        private const val SUBJECT = "Authentication"
        private const val VALIDITY_MILLIS = 24 * 60 * 60 * 1000L

        private val SECRET = System.getenv("JWT_SECRET") ?: "default secret"
        private val ALGORITHM = Algorithm.HMAC256(SECRET)
    }
}