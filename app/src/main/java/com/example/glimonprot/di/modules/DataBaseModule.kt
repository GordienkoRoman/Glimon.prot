package com.example.glimonprot.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.glimonprot.data.GlimonRepositoryImpl
import com.example.glimonprot.data.local.AppDataBase
import com.example.glimonprot.data.local.dao.CouponsDao
import com.example.glimonprot.data.local.dao.ReviewsDao
import com.example.glimonprot.data.local.dao.UsersDao
import com.example.glimonprot.di.components.AppScope
import com.example.glimonprot.domain.repository.GlimonRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

@Module
class DataBaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()

    }

    @AppScope
    @Provides
    fun provideUsersDao(appDataBase: AppDataBase): UsersDao = appDataBase.usersDao

    @AppScope
    @Provides
    fun provideReviewsDao(appDataBase: AppDataBase): ReviewsDao = appDataBase.reviewsDao

    @AppScope
    @Provides
    fun provideCouponsDao(appDataBase: AppDataBase): CouponsDao = appDataBase.couponsDao

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
    @AppScope
    @Provides
    fun provideGlimonRepository(usersDao: UsersDao,
                                reviewsDao: ReviewsDao,
                                couponsDao: CouponsDao,
                                dataStore: DataStore<Preferences>,
                                context : Context): GlimonRepository
    {
        return GlimonRepositoryImpl(
            usersDao = usersDao,
            couponsDao = couponsDao,
            reviewsDao = reviewsDao,
            dataStore = dataStore,
            context = context
        )
    }

//    @AppScope TODO()
//    @Provides
//    fun provideRoomRepository(usersDao: UsersDao,couponsDao: CouponsDao,reviewsDao: ReviewsDao, context: Context):RoomRepository {
//        return RoomRepository(usersDao = usersDao,couponsDao,reviewsDao, context = context)
//    }
}