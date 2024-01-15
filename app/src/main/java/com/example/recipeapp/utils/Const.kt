package com.example.recipeapp.utils

object Const {

    const val BASE_URL = "https://api.spoonacular.com/"
    const val CONNECTION_TIME = 20L

    //Register datastore
    const val REGISTER_USER_INFO = "register_user_info"
    const val REGISTER_USERNAME = "register_username"
    const val REGISTER_HASH = "register_hash"
    const val API_KEY_VALUE="b224fda4928f4d398ee50d77257c0c21"

    //APIs key
    const val API_KEY = "apiKey"
    const val SORT = "sort"
    const val NUMBER = "number"
    const val ADD_RECIPE_INFORMATION = "addRecipeInformation"
    const val TYPE = "type"
    const val DIET = "diet"
    const val QUERY = "query"

    //APIS value
    const val POPULARITY = "popularity"
    const val TRUE = "true"
    const val MAIN_COURSE = "main course"
    const val GLUTEN_FREE = "Gluten Free"

    //Database
    const val RECIPE_TABLE_NAME = "recipe_tbl"
    const val DB_NAME = "myDatabase"
    const val DETAIL_TABLE_NAME = "detail_tbl"
    const val FAVORITE_TABLE_NAME = "favorite_tbl"
    const val POPULAR_CACHE=0
    const val RECENT_CACHE=1

    // Menu Data Store
    const val MENU_DATASTORE = "menu_datastore"
    const val MENU_MEAL_TITLE_KEY = "menu_meal_title_key"
    const val MENU_MEAL_ID_KEY = "menu_meal_id_key"
    const val MENU_DIET_TITLE_KEY = "menu_diet_title_key"
    const val MENU_DIET_ID_KEY = "menu_diet_id_key"

    //Other
    const val OLD_IMAGE_SIZE = "312x231.jpg"
    const val NEW_IMAGE_SIZE = "636x393.jpg"
    const val REPEAT_TIME = 1000
    const val DELAY_TIME = 5000L
    const val LIMITED_COUNT = 10
    const val FULL_COUNT = 50
    var STEPS_COUNT = 0

}