package com.luka.berry.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luka.berry.network.UnsplashApi
import com.luka.berry.persistence.AppDatabase
import com.luka.berry.persistence.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

            .build()

    @Provides
    @Singleton
    fun provideUnSplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        callback: AppDatabase.CallBack
    ): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "users.db"
        ).fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope
