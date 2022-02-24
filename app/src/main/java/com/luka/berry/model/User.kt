package com.luka.berry.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    private val name: String,
    private val username: String
) : Parcelable {
}
