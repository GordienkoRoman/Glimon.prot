package com.example.glimonprot.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.model.ReviewItem

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = CouponsEntity::class,
            parentColumns = ["coupon_id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ReviewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "review") val review: String? = "",
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "is_useful") val isUseful: Boolean = false
) {
    companion object {
        fun fromReviewItem(reviewItem: ReviewItem, userId: String) =ReviewsEntity(
            0,
            reviewItem.review,
            userId,
            reviewItem.isUseful
        )
    }
    fun toReviewItem(feedItem: FeedItem): ReviewItem = ReviewItem(
        id = id,
        review = review.toString(),
        isUseful = isUseful,
        feedItem = feedItem
    )
}
