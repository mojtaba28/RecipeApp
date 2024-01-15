package com.example.recipeapp.models.data.datasource

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.preference.Preference
import android.provider.SyncStateContract.Constants
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipeapp.models.data.repository.RegisterRepository
import com.example.recipeapp.models.data_class.register.RegisterStoredModel
import com.example.recipeapp.utils.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class RegisterLocalDataSource ()  {

    private object StoredKeys{
        val username= stringPreferencesKey(Const.REGISTER_USERNAME)
        val hash = stringPreferencesKey(Const.REGISTER_HASH)
    }

    private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(Const.REGISTER_USER_INFO)



    suspend fun saveData(context: Context,username:String,hash:String){

        context.dataStore.edit {
            it[RegisterRepository.StoredKeys.username]=username
            it[RegisterRepository.StoredKeys.hash]=hash
        }
    }

    suspend fun  readData(context: Context):Flow<RegisterStoredModel>{
       return context.dataStore.data
            .catch {e->
                if (e is IOException){
                    emit(emptyPreferences())
                }
                else{
                    throw e
                }
            }. map {
                val username=it[StoredKeys.username]?:""
                val hash=it[StoredKeys.hash]?:""
                RegisterStoredModel(username, hash)
            }
    }



}