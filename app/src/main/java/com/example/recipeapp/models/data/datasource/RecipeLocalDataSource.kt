package com.example.recipeapp.models.data.datasource

import com.example.recipeapp.models.data.database.AppDao
import com.example.recipeapp.models.data.database.RecipeEntity
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(private val dao: AppDao) {

    suspend fun saveRecipesInDb(entity: RecipeEntity)=dao.saveRecipes(entity)
    fun getRecipesFromDb()=dao.getAllRecipes()
}