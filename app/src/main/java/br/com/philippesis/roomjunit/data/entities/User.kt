package br.com.philippesis.roomjunit.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_person")
data class User(
    @PrimaryKey val uid: Int = 0,
    @ColumnInfo(name = "name") var name: String = "unknown",
    @ColumnInfo(name = "email") var email: String = "unknown"
)
