package it.fabiovokrri.database

import it.fabiovokrri.database.tables.Tasks
import it.fabiovokrri.database.tables.UserTasksCrossRef
import it.fabiovokrri.database.tables.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun init() {
        Database.connect(
            url = "jdbc:h2:mem:tasks;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )

        transaction {
            SchemaUtils.create(Tasks)
            SchemaUtils.create(Users)
            SchemaUtils.create(UserTasksCrossRef)
        }
    }

    suspend fun <T> dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}