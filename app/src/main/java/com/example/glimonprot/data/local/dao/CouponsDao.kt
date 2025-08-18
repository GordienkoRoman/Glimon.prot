package com.example.glimonprot.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.glimonprot.data.local.entities.CouponsEntity

@Dao
interface CouponsDao {
    @Insert(entity = CouponsEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoupon(couponsEntity: CouponsEntity):Long

    @Query("SELECT * FROM coupons WHERE user_id = :userId")
    suspend fun getByUserId(userId: String): List<CouponsEntity>

    @Query("SELECT * FROM coupons WHERE coupon_id = :couponId")
    suspend fun getById(couponId: Long): CouponsEntity

    @Delete(entity = CouponsEntity::class)
    suspend fun deleteCoupon(couponsEntity: CouponsEntity)
}