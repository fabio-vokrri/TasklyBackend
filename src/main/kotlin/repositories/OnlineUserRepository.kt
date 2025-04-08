package it.fabiovokrri.repositories

import it.fabiovokrri.database.tables.Users
import it.fabiovokrri.database.utils.dbQuery
import it.fabiovokrri.database.utils.toUser
import it.fabiovokrri.models.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.koin.core.annotation.Single

@Single
class OnlineUserRepository : UserRepository {
    override suspend fun getById(id: Long): User? = dbQuery {
        Users.selectAll()
            .where { Users.id eq id }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun getByEmail(email: String): User? = dbQuery {
        Users.selectAll()
            .where { Users.email eq email }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun getByUsername(username: String): User? = dbQuery {
        Users.selectAll()
            .where { Users.name eq username }
            .map { it.toUser() }
            .firstOrNull()
    }

    override suspend fun insert(user: User): Boolean = dbQuery {
        val updatedRows = Users.insert {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[password] = password
        }

        updatedRows.insertedCount == 1
    }

    override suspend fun update(user: User): Boolean = dbQuery {
        val updatedRows = Users.update(
            where = { Users.id eq user.id },
            limit = 1,
        ) {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[password] = password
        }

        updatedRows == 1
    }

    override suspend fun delete(userId: Long): Boolean = dbQuery {
        val updatedRows = Users.deleteWhere { id eq userId }
        updatedRows == 1
    }
}