package com.example.recipeapp.models.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipeapp.models.data_class.menu_recipe.MenuStoredModel
import com.example.recipeapp.utils.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class MenuRepository @Inject constructor(@ApplicationContext private val context: Context){

    private object StoredKey{
        val mealTitle = stringPreferencesKey(Const.MENU_MEAL_TITLE_KEY)
        val mealId = intPreferencesKey(Const.MENU_MEAL_ID_KEY)
        val dietTitle = stringPreferencesKey(Const.MENU_DIET_TITLE_KEY)
        val dietId = intPreferencesKey(Const.MENU_DIET_ID_KEY)
    }

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(Const.MENU_DATASTORE)

     suspend fun saveData(meal: String, mealId: Int, diet: String, dietId: Int){
        context.datastore.edit {
            it[StoredKey.mealTitle]=meal
            it[StoredKey.mealId]=mealId
            it[StoredKey.dietTitle]=diet
            it[StoredKey.dietId]=dietId


        }
    }

//    val readData: Flow<MenuStoredModel> = context.datastore.data
//        .catch {e ->
//            if (e is IOException) {
//                emit(emptyPreferences())
//            } else {
//                throw e
//            }
//        } .map {
//            val meal = it[StoredKey.mealTitle] ?: Const.MAIN_COURSE
//            val mealId = it[StoredKey.mealId] ?: 0
//            val diet = it[StoredKey.dietTitle] ?: Const.GLUTEN_FREE
//            val dietId = it[StoredKey.dietId] ?: 0
//            MenuStoredModel(meal, mealId, diet, dietId)
//        }

     fun readData(): Flow<MenuStoredModel>{
        return context.datastore.data
            .catch {e ->
                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            } .map {
                val meal = it[StoredKey.mealTitle] ?: Const.MAIN_COURSE
                val mealId = it[StoredKey.mealId] ?: 0
                val diet = it[StoredKey.dietTitle] ?: Const.GLUTEN_FREE
                val dietId = it[StoredKey.dietId] ?: 0
                MenuStoredModel(meal, mealId, diet, dietId)
            }
    }
}