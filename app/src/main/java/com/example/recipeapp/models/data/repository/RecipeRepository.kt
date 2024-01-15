package com.example.recipeapp.models.data.repository

import com.example.recipeapp.models.data.database.RecipeEntity
import com.example.recipeapp.models.data.datasource.RecipeLocalDataSource
import com.example.recipeapp.models.data.datasource.RecipeRemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipeRepository @Inject constructor(private val remoteDataSource: RecipeRemoteDataSource
,private val localDataSource: RecipeLocalDataSource){

    suspend fun getRecipes(queries: Map<String, String>)= remoteDataSource.getRecipes(queries)

    //local
    suspend fun saveRecipesInDb(entity: RecipeEntity)=localDataSource.saveRecipesInDb(entity)
    fun getRecipesFromDb()=localDataSource.getRecipesFromDb()
}