package com.example.glimonprot.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.glimonprot.domain.model.FeedItem

@Entity(
    tableName = "coupons"
)
data class CouponsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coupon_id")  val couponId: Int,
    @ColumnInfo(name = "user_id") val userId: String?,
    @ColumnInfo(name = "company_name") val companyName: String?,
    @ColumnInfo(name = "promotion_name") val promotionName: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "img_url") val imgUrl: String?
) {
    companion object {
        fun fromFeedItem(feedItem: FeedItem, userId: String) = CouponsEntity(
            0,
            userId = userId,
            companyName = feedItem.companyName,
            promotionName = feedItem.promotionName,
            description = feedItem.description,
            location = feedItem.location,
            imgUrl = feedItem.imgUrl
        )
    }

    fun toFeedItem(): FeedItem = FeedItem(
        id = couponId!!,
        companyName = companyName.toString(),
        promotionName = promotionName.toString(),
        description = description.toString(),
        location = location.toString(),
        imgUrl = imgUrl.toString()
    )
}
