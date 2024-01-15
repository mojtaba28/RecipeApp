package com.example.recipeapp.models.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recipeapp.utils.Const
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface AppDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveRecipes(entity: RecipeEntity)

    @Query("SELECT * FROM ${Const.RECIPE_TABLE_NAME} ORDER BY ID ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}