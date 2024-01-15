package com.example.recipeapp.di

import com.example.recipeapp.models.data.datasource.RegisterLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceProvider {

    @Provides
    fun provideRegisterLocalDataSource(): RegisterLocalDataSource {
        return RegisterLocalDataSource()
    }

}