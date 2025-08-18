package com.example.glimonprot.data.remote.userApi

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.glimonprot.data.remote.RemoteUser

interface OkApi {
    @GET("users.getInfo")
    suspend fun getCurrentUser(
        @Query("access_token") token: String
    ): RemoteUser
}