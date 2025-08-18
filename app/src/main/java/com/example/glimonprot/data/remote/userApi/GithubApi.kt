package com.example.glimonprot.data.remote.userApi

import retrofit2.http.GET
import com.example.glimonprot.data.remote.RemoteUser

interface GithubApi {
    @GET("user")
    suspend fun getCurrentUser(
    ): RemoteUser.RemoteGithubUser
}