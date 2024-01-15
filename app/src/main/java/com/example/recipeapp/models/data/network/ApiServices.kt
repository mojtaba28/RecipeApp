package com.example.recipeapp.models.data.network

import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.example.recipeapp.models.data_class.register.BodyRegister
import com.example.recipeapp.models.data_class.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiServices {

    @POST("users/connect")
    suspend fun register(@Query("apiKey") apiKey:String ,@Body body: BodyRegister)
    :Response<ResponseRegister>

    @GET("recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries:Map<String,String>):Response<ResponseRecipes>


}