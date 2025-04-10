package it.fabiovokrri.repositories

import at.favre.lib.crypto.bcrypt.BCrypt
import it.fabiovokrri.database.Database.query
import it.fabiovokrri.database.mappers.toUser
import it.fabiovokrri.database.tables.Users
import it.fabiovokrri.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.annotation.Single

@Single
class OnlineUserRepository : UserRepository {
    override suspend fun getById(id: Long): User? = query {
        Users.selectAll()
            .where { Users.id eq id }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun getByEmail(email: String): User? = query {
        Users.selectAll()
            .where { Users.email eq email }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun getByUsername(username: String): User? = query {
        Users.selectAll()
            .where { Users.name eq username }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun insert(user: User): Boolean = query {
        val alreadyExists = Users.selectAll()
            .where { (Users.name eq user.name) or (Users.email eq user.email) }
            .count() == 0L

        if (alreadyExists) return@query false

        val updatedRows = Users.insert {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password!!
        }

        updatedRows.insertedCount == 1
    }

    override suspend fun update(user: User): Boolean = query {
        val updatedRows = Users.update(
            where = { Users.id eq user.id },
            limit = 1,
        ) {
            it[name] = user.name
            it[email] = user.email
            it[password] = password
        }

        updatedRows == 1
    }

    override suspend fun delete(userId: Long): Boolean = query {
        val updatedRows = Users.deleteWhere { id eq userId }
        updatedRows == 1
    }

    override suspend fun validateCredentials(userName: String, password: String): User? = query {
        val user: User = Users.selectAll()
            .where { Users.name eq userName }
            .firstOrNull()
            ?.toUser()
            ?: return@query null

        val result = BCrypt.verifyer().verify(
            password.toCharArray(),
            user.password?.toCharArray()
        )

        if (result.verified) user else null
    }
}