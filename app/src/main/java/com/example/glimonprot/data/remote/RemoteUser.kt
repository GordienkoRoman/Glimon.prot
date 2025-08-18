package com.example.glimonprot.data.remote

import com.squareup.moshi.JsonClass


sealed class RemoteUser{
    @JsonClass(generateAdapter = false)
    data class RemoteGithubUser(
        val id: Long,
        val name: String?,
        val bio: String?,
        val email: String?,
        val avatar_url: String?,
    )
    @JsonClass(generateAdapter = false)
    data class RemoteMailUser(
        val email: String?,
        val first_name: String?,
        val last_name: String?,
        val birthday: String?,
        val sex: Int?
    )
    @JsonClass(generateAdapter = false)
    data class RemoteOkUser (
        val name: String,
        val age: Int
    )

}
