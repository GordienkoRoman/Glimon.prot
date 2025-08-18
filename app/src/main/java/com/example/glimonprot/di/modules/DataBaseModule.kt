package com.example.glimonprot.di.modules

import android.content.Context
import androidx.room.Room
import com.example.glimonprot.data.local.AppDataBase
import com.example.glimonprot.data.local.dao.CouponsDao
import com.example.glimonprot.data.local.dao.ReviewsDao
import com.example.glimonprot.data.local.dao.UsersDao
import com.example.glimonprot.di.components.AppScope
import dagger.Module
import dagger.Provides

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

//    @AppScope TODO()
//    @Provides
//    fun provideRoomRepository(usersDao: UsersDao,couponsDao: CouponsDao,reviewsDao: ReviewsDao, context: Context):RoomRepository {
//        return RoomRepository(usersDao = usersDao,couponsDao,reviewsDao, context = context)
//    }
}