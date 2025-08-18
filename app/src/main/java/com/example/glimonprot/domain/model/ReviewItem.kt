package com.example.glimonprot.domain.model

data class ReviewItem(
    val id:Long = 0,
    val review:String,
    val isUseful:Boolean,
    val feedItem: FeedItem
)
