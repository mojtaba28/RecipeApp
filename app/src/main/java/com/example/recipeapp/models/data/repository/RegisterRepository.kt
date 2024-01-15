package com.example.recipeapp.models.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipeapp.models.data.datasource.RegisterLocalDataSource
import com.example.recipeapp.models.data.datasource.RegisterRemoteDataSource
import com.example.recipeapp.models.data_class.register.BodyRegister
import com.example.recipeapp.models.data_class.register.RegisterStoredModel
import com.example.recipeapp.utils.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepository @Inject constructor(

    @ApplicationContext private val context: Context,
    private val registerRemoteDataSource: RegisterRemoteDataSource,
    private val localDataSource: RegisterLocalDataSource
    ) {

    suspend fun register(apiKey:String,body: BodyRegister)=registerRemoteDataSource.register(apiKey, body)

    object StoredKeys{
        val username= stringPreferencesKey(Const.REGISTER_USERNAME)
        val hash = stringPreferencesKey(Const.REGISTER_HASH)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Const.REGISTER_USER_INFO)
      suspend fun  saveData(username:String,hash:String) = localDataSource.saveData(context,username, hash)
       suspend fun  readData():Flow<RegisterStoredModel> = localDataSource.readData(context)


}