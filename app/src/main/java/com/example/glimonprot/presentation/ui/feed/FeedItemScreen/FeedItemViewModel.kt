package stud.gilmon.presentation.ui.feed.FeedItemScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stud.gilmon.data.model.FeedItem
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject

class FeedItemViewModel @Inject constructor(
    context: Context,
    private val roomRepository: RoomRepository,
) : ViewModel() {

    fun insertCoupon(feedItem: FeedItem, userId:String){
        viewModelScope.launch {
            roomRepository.insertCoupon(feedItem,userId)
        }
    }
    fun insertReview(feedItem: FeedItem, userId:String,review:String){
        viewModelScope.launch {
            roomRepository.insertReview(feedItem, userId,review)
        }
    }
}