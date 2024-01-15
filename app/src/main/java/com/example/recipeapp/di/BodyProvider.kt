package com.example.recipeapp.di

import com.example.recipeapp.models.data_class.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object BodyProvider {

    @Provides
    fun bodyRegister()=BodyRegister()
}