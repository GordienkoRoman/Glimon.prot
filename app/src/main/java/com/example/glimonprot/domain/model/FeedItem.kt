package com.example.glimonprot.domain.model

data class FeedItem(
    val id :Int=0,
    val companyName: String,
    val promotionName: String,
    val description: String,
    val location: String,
    val imgUrl: String,
    val reviews: List<String>?=null,
    val downloads:Int =0,
    val likes:Int =0
)
{
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$id", "$description", "$location",
            "$promotionName", "$companyName"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
