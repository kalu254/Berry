package com.luka.berry.persistence

import androidx.room.*
import com.luka.berry.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
     fun getUsers() : Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}
