package com.example.glimonprot.presentation.ui.feed.feedItemScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.repository.GlimonRepository
import javax.inject.Inject

class FeedItemViewModel @Inject constructor(
    context: Context,
    private val glimonRepository: GlimonRepository,
) : ViewModel() {

    fun insertCoupon(feedItem: FeedItem, userId:String){
        viewModelScope.launch {

          glimonRepository.insertCoupon(feedItem,userId)
        }
    }
    fun insertReview(feedItem: FeedItem, userId:String,review:String){
        viewModelScope.launch {
            glimonRepository.insertReview(feedItem, userId,review)
        }
    }
}