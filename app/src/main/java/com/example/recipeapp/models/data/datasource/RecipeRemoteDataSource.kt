package com.example.recipeapp.models.data.datasource

import com.example.recipeapp.models.data.network.ApiServices
import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getRecipes(queries: Map<String, String>) = apiServices.getRecipes(queries)
}