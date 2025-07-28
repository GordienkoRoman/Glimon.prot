package stud.gilmon.presentation.ui.profile.settings

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.domain.DataStoreRepository
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val dataStoreRepository: DataStoreRepository,
    val context: Context
) : ViewModel(){

    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore


    fun updateUserData(usersEntity: UsersEntity){
        viewModelScope.launch {
            roomRepository.upsertUser(usersEntity)
            Toast.makeText(context, "User updated", Toast.LENGTH_LONG).show()
        }
    }

    fun setUser(onClick: () ->Unit) {
        viewModelScope.launch {
            dataStoreRepository.setUser("")
            onClick()
        }
    }

    val darkThemeFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val uiMode = preferences[darkModeKey] ?: false
            uiMode
        }
    companion object {
        val darkModeKey = booleanPreferencesKey("DARK_MODE_KEY")
    }


    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch { dataStore.edit { preferences ->
            preferences[darkModeKey] = isDarkMode
        } }
    }


    fun getTheme(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val uiMode = preferences[darkModeKey] ?: false
                uiMode
            }
    }


}
