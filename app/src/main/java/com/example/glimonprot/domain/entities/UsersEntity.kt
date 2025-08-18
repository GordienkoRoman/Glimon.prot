package com.example.glimonprot.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UsersEntity(
    @ColumnInfo(name = "first_name") val firstName: String = "",
    @ColumnInfo(name = "last_name") val lastName: String = "",
    @ColumnInfo(name = "gender") val gender: String = "",
    @ColumnInfo(name = "age") val age: String = "",
    @ColumnInfo(name = "family_status") val familyStatus: String = "",
    @ColumnInfo(name = "about_me") val aboutMe: String = "",
    @ColumnInfo(name = "number") val number: String = "",
    @ColumnInfo(name = "mail") val mail: String = "",
    @ColumnInfo(name = "password") val password: String = "",
    @ColumnInfo(name = "notification")val notifications: Boolean = false,
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "avatar_url") val avatarUrl:String = "",
    @ColumnInfo(name = "review_id")val reviewId: Long=0,
     @PrimaryKey
    val userId: String,
) {

    /*  companion object {
          const val TABLE_NAME = "accounts"
      }*/
}
