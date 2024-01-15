package com.example.recipeapp.utils

fun popularRecipesQuery(): HashMap<String, String> {
    val queries: HashMap<String, String> = HashMap()
    queries[Const.API_KEY] = Const.API_KEY_VALUE
    queries[Const.SORT] = Const.POPULARITY
    queries[Const.NUMBER] = Const.LIMITED_COUNT.toString()
    queries[Const.ADD_RECIPE_INFORMATION] = Const.TRUE
    return queries
}

fun recentRecipesQuery(): HashMap<String,String>{
    val queries: HashMap<String, String> = HashMap()
    queries[Const.API_KEY] = Const.API_KEY_VALUE
    queries[Const.TYPE] = Const.TYPE
    queries[Const.DIET] = Const.DIET
    queries[Const.NUMBER] = Const.FULL_COUNT.toString()
    queries[Const.ADD_RECIPE_INFORMATION] = Const.TRUE
    return queries
}