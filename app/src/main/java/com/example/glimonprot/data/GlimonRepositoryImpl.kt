package com.example.glimonprot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.glimonprot.data.local.dao.CouponsDao
import com.example.glimonprot.data.local.dao.ReviewsDao
import com.example.glimonprot.data.local.dao.UsersDao
import com.example.glimonprot.data.local.entities.CouponsEntity
import com.example.glimonprot.data.local.entities.ReviewsEntity
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.model.ReviewItem
import com.example.glimonprot.domain.repository.GlimonRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import stud.gilmon.data.local.entities.UsersEntity
import java.io.IOException
import javax.inject.Inject

class GlimonRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val couponsDao: CouponsDao,
    private val reviewsDao: ReviewsDao,
    private val dataStore: DataStore<Preferences>,
    val context: Context
): GlimonRepository {

    companion object {
         private val Context.dataStore by preferencesDataStore(name = "settings")
        val userKey = stringPreferencesKey("USER_KEY")

    }

    val loginFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val login = preferences[userKey] ?: false
            login
        }

    override suspend fun setPrefUser(login: String) {
        dataStore.edit { preferences ->
            preferences[userKey] = login
        }
    }

    override suspend fun upsertUser(usersEntity: UsersEntity) {
        usersDao.upsertUser(usersEntity)
    }

    override suspend fun insertCoupon(feedItem: FeedItem, userId: String) {
        couponsDao.insertCoupon(CouponsEntity.fromFeedItem(feedItem, userId))
    }

    override suspend fun deleteCoupon(feedItem: FeedItem, userId: String) {
        couponsDao.deleteCoupon(
            couponsEntity = CouponsEntity.fromFeedItem(
                feedItem,
                userId
            )
        )
    }

    override suspend fun insertReview(feedItem: FeedItem, userId: String, review: String) {
        val couponsEntity = CouponsEntity.fromFeedItem(feedItem, "")
        val id = couponsDao.insertCoupon(couponsEntity)
        reviewsDao.insertReview(ReviewsEntity(id = id, review = review, userId = userId))
    }

    override suspend fun deleteReview(reviewItem: ReviewItem, userId: String) {
        reviewsDao.deleteReview(ReviewsEntity.fromReviewItem(reviewItem, userId))
    }

    override suspend fun getUser(login: String): UsersEntity? {
        return usersDao.getUserByLogin(login)
    }

    override suspend fun getCoupons(login: String): List<FeedItem> {
        return couponsDao.getByUserId(login).map { it.toFeedItem() }
    }

    override suspend fun getReviews(login: String): List<ReviewItem> {
        return reviewsDao.getById(login).map {
            val feedItem = couponsDao.getById(it.id).toFeedItem()
            it.toReviewItem(feedItem)
        }
    }


}