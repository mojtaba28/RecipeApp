package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.data.repository.MenuRepository
import com.example.recipeapp.models.data_class.menu_recipe.MenuStoredModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val repository: MenuRepository):ViewModel() {

    fun mealsList():MutableList<String>{
        return mutableListOf( "Main Course", "Bread", "Marinade",
            "Side Dish", "Breakfast", "Dessert", "Soup",
            "Snack", "Appetizer",
            "Beverage", "Drink", "Salad", "Sauce")
    }

    fun dietsList(): MutableList<String> {
        return mutableListOf("Gluten Free", "Ketogenic", "Vegetarian",
            "Vegan", "Pescetarian", "Paleo")
    }

    fun saveData(meal:String,mealID:Int,diet:String,dietID:Int)=viewModelScope.launch {
        repository.saveData(meal,mealID,diet,dietID)
    }

    fun readData(): Flow<MenuStoredModel> {
        return repository.readData()
    }



}