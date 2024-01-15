package com.example.recipeapp.viewmodel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import com.example.recipeapp.models.data.database.RecipeEntity
import com.example.recipeapp.models.data.repository.MenuRepository
import com.example.recipeapp.models.data.repository.RecipeRepository
import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.example.recipeapp.utils.Const
import com.example.recipeapp.utils.NetworkRequestState
import com.example.recipeapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor
    (private val repository: RecipeRepository,
     private val menuRepository: MenuRepository):ViewModel() {

    val popularLiveData = MutableLiveData<NetworkRequestState<ResponseRecipes>>()
    val recentLiveData=MutableLiveData<NetworkRequestState<ResponseRecipes>>()



    fun getPopularRecipes(query:Map<String,String>)=viewModelScope.launch {
        popularLiveData.value=NetworkRequestState.Loading()
        val response=repository.getRecipes(query)
        popularLiveData.value = NetworkResponse(response).responseResult()

        //cache
        val cacheData=popularLiveData.value?.data
        if(cacheData!= null){
            saveRecipesInDb(cacheData,Const.POPULAR_CACHE)
        }
    }


    fun getRecentRecipes( query: HashMap<String, String>)=viewModelScope.launch {
        recentLiveData.value=NetworkRequestState.Loading()
        val response= repository.getRecipes(query)
        recentLiveData.value=recentNetworkResponse(response)

        //cache
        val cacheData=recentLiveData.value?.data
        if(cacheData!= null){
            saveRecipesInDb(cacheData,Const.RECENT_CACHE)
        }

    }

    //local
    fun saveRecipesInDb(response: ResponseRecipes,recipeType:Int)= viewModelScope.launch(Dispatchers.IO){
        val entity = RecipeEntity(recipeType, response)
        repository.saveRecipesInDb(entity)
    }

    fun getRecipesFromDb()= repository.getRecipesFromDb().asLiveData()



    private var mealType = Const.MAIN_COURSE
    private var dietType = Const.GLUTEN_FREE

    fun recentQueries(): HashMap<String, String> {
        viewModelScope.launch {
            menuRepository.readData().collect {
                mealType = it.meal
                dietType = it.diet
            }
        }
        val queries: HashMap<String, String> = HashMap()
        queries[Const.API_KEY] = Const.API_KEY_VALUE
        queries[Const.TYPE] = mealType
        queries[Const.DIET] = dietType
        queries[Const.NUMBER] = Const.FULL_COUNT.toString()
        queries[Const.ADD_RECIPE_INFORMATION] = Const.TRUE
        return queries
    }


    private fun recentNetworkResponse(response: Response<ResponseRecipes>): NetworkRequestState<ResponseRecipes> {
        return when {
            response.message().contains("timeout") -> NetworkRequestState.Error("Timeout")
            response.code() == 401 -> NetworkRequestState.Error("You are not authorized")
            response.code() == 402 -> NetworkRequestState.Error("Your free plan finished")
            response.code() == 422 -> NetworkRequestState.Error("Api key not found!")
            response.code() == 500 -> NetworkRequestState.Error("Try again")
            response.body()!!.results.isNullOrEmpty() -> NetworkRequestState.Error("Not found any recipe!")
            response.isSuccessful -> NetworkRequestState.Success(response.body()!!)
            else -> NetworkRequestState.Error(response.message())
        }
    }




}