package com.example.recipeapp.models.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.example.recipeapp.utils.Const

@Entity(tableName = Const.RECIPE_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseRecipes
)
