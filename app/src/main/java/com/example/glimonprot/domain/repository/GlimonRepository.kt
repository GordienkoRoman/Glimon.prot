package com.example.glimonprot.domain.repository

import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.model.ReviewItem
import com.example.glimonprot.domain.entities.UsersEntity

interface GlimonRepository {

    suspend fun getCoupons(login: String): List<FeedItem>

    suspend fun getReviews(login: String): List<ReviewItem>

    suspend fun upsertUser(usersEntity: UsersEntity)

    suspend fun insertCoupon(feedItem: FeedItem, userId: String)

    suspend fun deleteCoupon(feedItem: FeedItem, userId: String)

    suspend fun insertReview(feedItem: FeedItem, userId: String, review: String)

    suspend fun deleteReview(reviewItem: ReviewItem, userId: String)

    suspend fun getUser(login: String): UsersEntity?

    suspend fun setPrefUser(login: String)
}