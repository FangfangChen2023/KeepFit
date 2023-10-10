package com.example.KeepFit.di

import android.app.Application
import androidx.room.Room
import com.example.KeepFit.data.AppDatabase
import com.example.KeepFit.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app:Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "keep_fit"
        ).build()
    }
    @Provides
    @Singleton
    fun provideHistoryRepository(db:AppDatabase):HistoryRepository{
        return HistoryRepositoryImpl(db.historyDao())
    }
    @Provides
    @Singleton
    fun provideGoalRepository(db:AppDatabase):GoalRepository{
        return GoalRepositoryImpl(db.goalDao())
    }
    @Provides
    @Singleton
    fun provideDailyRepository(db:AppDatabase): DailyRepository {
        return DailyRepositoryImpl(db.dailyDao())
    }
}
