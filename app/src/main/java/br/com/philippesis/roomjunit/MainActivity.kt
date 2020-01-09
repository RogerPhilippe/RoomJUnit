package br.com.philippesis.roomjunit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.philippesis.roomjunit.data.db.DatabaseHandler
import br.com.philippesis.roomjunit.data.db.GetDatabase
import br.com.philippesis.roomjunit.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = GetDatabase.getDatabaseBuilder(this)

        println("Insert if empty.")

        if (findAll(db).isEmpty()) {
            val user = User(0, "Person 1", "person1@email.com")
            insert(db, user)
        }

        printAllUsers(db)

        println("Update person.")
        val userToUpdate = findAll(db)[0]
        userToUpdate.name = "${userToUpdate.name} updated"
        update(db, userToUpdate)

        printAllUsers(db)

        println("Delete person.")
        val users = findAll(db)
        if (users.isEmpty()) {
            println("Não existem pessoas registradas!")
        } else {
            delete(db, users[0])
            printAllUsers(db)
        }

    }

    private fun insert(db: DatabaseHandler, user: User) = runBlocking {
        withContext(Dispatchers.IO) { db.userDAO().insert(user) }
    }

    private fun findAll(db: DatabaseHandler) = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            db.userDAO().getAll()
        }
    }

    private fun update(db: DatabaseHandler, user: User) = runBlocking {
        withContext(Dispatchers.IO) { db.userDAO().update(user) }
    }

    private fun delete(db: DatabaseHandler, user: User) = runBlocking {
        withContext(Dispatchers.IO) { db.userDAO().delete(user) }
    }

    private fun printAllUsers(db: DatabaseHandler) = runBlocking {
        withContext(Dispatchers.IO) {
            findAll(db).forEach{ println("User: $it") }
        }
    }

}
