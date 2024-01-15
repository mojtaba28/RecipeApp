package com.example.recipeapp.models.data.database


import androidx.room.TypeConverter
import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.google.gson.Gson

class RecipeTypeConvertor {
    private val gson=Gson()

    @TypeConverter
    fun recipeToJson(recipe: ResponseRecipes): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): ResponseRecipes {
        return gson.fromJson(data, ResponseRecipes::class.java)
    }
}