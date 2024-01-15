package com.example.recipeapp.utils

import retrofit2.Response

open class NetworkResponse<T>(private val response: Response<T>) {

    fun responseResult():NetworkRequestState<T>{
       return when {
           response.message().contains("timeout")->NetworkRequestState.Error("Timeout.")
           response.code()==401->NetworkRequestState.Error("You are not authorized.")
           response.code() == 402 -> NetworkRequestState.Error("Your free plan finished.")
           response.code() == 422 -> NetworkRequestState.Error("Api key not found!")
           response.code() == 500 -> NetworkRequestState.Error("Try again!")
           response.isSuccessful->NetworkRequestState.Success(response.body()!!)
           else->NetworkRequestState.Error(response.message())
       }
    }
}