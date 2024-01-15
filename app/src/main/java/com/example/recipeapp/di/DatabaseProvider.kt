package com.example.recipeapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.models.data.database.RecipeAppDatabase
import com.example.recipeapp.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseProvider {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RecipeAppDatabase::class.java, Const.DB_NAME
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(database: RecipeAppDatabase) = database.getDao()
}