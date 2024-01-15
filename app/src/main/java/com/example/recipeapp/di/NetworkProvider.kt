package com.example.recipeapp.di

import com.example.recipeapp.models.data.network.ApiServices
import com.example.recipeapp.utils.Const.BASE_URL
import com.example.recipeapp.utils.Const.CONNECTION_TIME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient( interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .writeTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        .readTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIME, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit( gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
}