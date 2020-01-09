package br.com.philippesis.roomjunit.data.db.dao

import androidx.room.*
import br.com.philippesis.roomjunit.data.entities.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM tb_person")
    fun getAll(): List<User>

    @Query("SELECT * FROM tb_person WHERE uid LIKE :uid")
    fun findByID(uid: Int): User

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}