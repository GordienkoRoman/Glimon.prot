package com.example.glimonprot.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos/random")
    suspend fun getRandomPhotos(
        @Query("client_id") key: String,
        @Query("count") count: Int
    ): List<UnsplashDto>

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") key: String,
        @Query("page")  page: Int ,
        @Query("per_page") perPage: Int,
    ): Response<List<UnsplashDto>>
}
