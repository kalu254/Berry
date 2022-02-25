package com.luka.berry.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.luka.berry.di.ApplicationScope
import com.luka.berry.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


    class CallBack @Inject constructor(
        private val database: Provider<AppDatabase>,
       @ApplicationScope private val applicationScone: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)


            val dao = database.get().userDao()

            applicationScone.launch {
                dao.insertUser(User(1, "Luka", "Kalu"))
                dao.insertUser(User(1, "Eugene", "Chweez"))
                dao.insertUser(User(1, "Joshua", "Josh"))
                dao.insertUser(User(1, "Martin", "kim"))
                dao.insertUser(User(1, "Kenneth", "Ken"))
            }


        }
    }
}
