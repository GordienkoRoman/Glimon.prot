package com.example.glimonprot.data.remote

import com.example.glimonprot.domain.model.FeedItem
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = false)
data class UnsplashDto(
    val id: String?="",
    val likes: Int?=0,
    val downloads: Int? = 0,
    val description:String? = "",
    val urls: Urls?=Urls(),
    val links: Links? = Links(),
    val location: Location = Location(),
    val user: User = User()
)
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$id", "$description", "$user.location",
            "$location.name", "$user.bio","$user.name",
            "$user.username"
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
fun UnsplashDto.toFeedItem(): FeedItem
{
return FeedItem(id = 0,  companyName = user.name ?: "name",
    promotionName =user.name ?: "name",
    description =description ?:user.bio ?: "Description",
    location =location.name ?:user.location ?: "Location",
    imgUrl =urls?.raw.toString())
}

@JsonClass(generateAdapter = false)
data class Urls(
    val raw: String?="",
)
@JsonClass(generateAdapter = false)
data class Links(
    val download: String?="",
)
@JsonClass(generateAdapter = false)
data class Location(
    val name: String?="",
)
@JsonClass(generateAdapter = false)
data class User(
    val name: String?="",
    val username: String?="",
    val bio: String?="",
    val location: String?=""
)