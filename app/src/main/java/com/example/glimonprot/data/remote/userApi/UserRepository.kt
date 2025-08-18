package com.example.glimonprot.data.remote.userApi

import javax.inject.Inject


class UserRepository @Inject constructor(
    private val githubApi: GithubApi
   // private val mailApi: MailApi?
) {

   suspend fun getUser(){
        githubApi.getCurrentUser()
    }
}