package com.example.glimonprot.data

import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.model.ReviewItem
import com.example.glimonprot.domain.repository.GlimonRepository
import stud.gilmon.data.local.entities.UsersEntity
import javax.inject.Inject

class GlimonRepositoryImpl @Inject constructor(): GlimonRepository {
    override suspend fun getCoupons(login: String): List<FeedItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviews(login: String): List<ReviewItem> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertUser(usersEntity: UsersEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCoupon(
        feedItem: FeedItem,
        userId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCoupon(
        feedItem: FeedItem,
        userId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun insertReview(
        feedItem: FeedItem,
        userId: String,
        review: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReview(
        reviewItem: ReviewItem,
        userId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(login: String): UsersEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun setPrefUser(login: String) {
        TODO("Not yet implemented")
    }
}