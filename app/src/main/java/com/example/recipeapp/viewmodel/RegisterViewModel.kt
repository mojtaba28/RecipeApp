package com.example.recipeapp.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.models.data.repository.RegisterRepository
import com.example.recipeapp.models.data_class.register.BodyRegister
import com.example.recipeapp.models.data_class.register.RegisterStoredModel
import com.example.recipeapp.models.data_class.register.ResponseRegister
import com.example.recipeapp.utils.NetworkRequestState
import com.example.recipeapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    val registerLiveData = MutableLiveData<NetworkRequestState<ResponseRegister>>()

    fun register(apiKey: String, body: BodyRegister) = viewModelScope.launch {
        registerLiveData.value = NetworkRequestState.Loading()
        val response = repository.register(apiKey, body)
        registerLiveData.value = NetworkResponse(response).responseResult()
    }

    // Stored data
    fun saveData(username: String, hash: String) = viewModelScope.launch {
        repository.saveData(username, hash)
    }

    // Function to read data and observe it in your fragment
    suspend fun readData(): Flow<RegisterStoredModel> {

            return repository.readData()
    }
}