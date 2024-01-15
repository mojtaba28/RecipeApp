package com.example.recipeapp.models.data.database



import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConvertor::class)
abstract class RecipeAppDatabase:RoomDatabase() {
    abstract fun getDao():AppDao
}