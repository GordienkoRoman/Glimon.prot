package com.example.glimonprot.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import stud.gilmon.data.local.entities.UsersEntity

@Dao
interface UsersDao {
    @Upsert(entity = UsersEntity::class)
    suspend fun upsertUser(userEntity: UsersEntity)


    @Query("SELECT * FROM users WHERE userId = :login")
    suspend fun getUserByLogin(login:String): UsersEntity?
}