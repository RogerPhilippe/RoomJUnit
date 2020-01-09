package br.com.philippesis.roomjunit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.philippesis.roomjunit.data.db.dao.UserDAO
import br.com.philippesis.roomjunit.data.entities.User

@Database(entities = [User::class], version = 1)
abstract class DatabaseHandler: RoomDatabase() {
    abstract fun userDAO(): UserDAO
}

object GetDatabase {

    private const val databaseName = "room.db"

    fun getDatabaseBuilder(context: Context): DatabaseHandler {
        return Room.databaseBuilder(
            context, DatabaseHandler::class.java, databaseName
        ).build()
    }

}
