package com.example.recipeapp.utils

sealed class NetworkRequestState<T>(val data: T? = null, val message: String? = null){
    class Loading<T>:NetworkRequestState<T>()
    class Success<T>(data: T?):NetworkRequestState<T>(data)
    class Error<T>(message: String,data: T?=null):NetworkRequestState<T>(data, message)
}
