package com.example.glimonprot.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.glimonprot.di.components.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@Module
class DataStoreModule {
    @AppScope
    @Provides
    fun provideDataStore(
         context: Context,
         @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(ioDispatcher),
            produceFile = { context.preferencesDataStoreFile("settings") }
        )
    }

//    @AppScope TODO()
//    @Provides
//    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreOperations =
//        DataStoreRepository(dataStore)
}