package br.com.philippesis.roomjunit.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.philippesis.roomjunit.data.db.dao.UserDAO
import br.com.philippesis.roomjunit.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.core.Is
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [21])
class DatabaseTest {

    private lateinit var context: Context
    private lateinit var userDAO: UserDAO
    private lateinit var db: DatabaseHandler

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseHandler::class.java
        ).build()
        userDAO = db.userDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testDatabase() {
        val db = GetDatabase.getDatabaseBuilder(context)
        Assert.assertNotNull(db)
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadTest() = runBlocking {
        var firstUser = listOf<User>()
        val user = User(0, "User 1", "user.1@email.com")
        withContext(Dispatchers.IO) {
            userDAO.insert(user)
            firstUser = userDAO.getAll()
        }
        Assert.assertThat(firstUser[0], equalTo(user))
    }

    @Test
    @Throws
    fun findUserByIdTest() = runBlocking {
        var userRecovered = User()
        val user = User(0, "User 2", "user.2@email.com")
        withContext(Dispatchers.IO) {
            userDAO.insert(user)
            val id = userDAO.getAll()[0].uid
            userRecovered = userDAO.findByID(id)
        }
        Assert.assertThat(userRecovered, equalTo(user))
    }

    @Test
    @Throws
    fun updateTest() = runBlocking {
        val nameChanged = "User 3"
        val emailChanged = "user.3@email.com"
        var userRecovered = User()
        val user = User(0, "User 2", "user.2@email.com")
        withContext(Dispatchers.IO) {
            userDAO.insert(user)
            val id = userDAO.getAll()[0].uid
            userRecovered = userDAO.findByID(id)
            userRecovered.name = nameChanged
            userRecovered.email = emailChanged
            userDAO.update(userRecovered)
            userRecovered = userDAO.findByID(id)
        }
        Assert.assertThat(userRecovered.name, equalTo(nameChanged))
        Assert.assertThat(userRecovered.email, equalTo(emailChanged))
    }

    @Test
    @Throws
    fun deleteTest() = runBlocking {
        var userRecovered = listOf<User>()
        val user = User(0, "User 3", "user.3@email.com")
        withContext(Dispatchers.IO) {
            userDAO.insert(user)
            userDAO.delete(user)
            userRecovered = userDAO.getAll()
        }
        Assert.assertThat(userRecovered.size, Is.`is`(0))
    }

}