package com.example.glimonprot.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.glimonprot.data.local.dao.CouponsDao
import com.example.glimonprot.data.local.dao.ReviewsDao
import com.example.glimonprot.data.local.dao.UsersDao
import com.example.glimonprot.data.local.entities.CouponsEntity
import com.example.glimonprot.data.local.entities.ReviewsEntity
import stud.gilmon.data.local.entities.UsersEntity

@Database(
    entities = [UsersEntity::class,
        ReviewsEntity::class,
        CouponsEntity::class
],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val couponsDao: CouponsDao
    abstract val reviewsDao: ReviewsDao
}