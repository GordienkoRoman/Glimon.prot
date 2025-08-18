package example.glimonprot.presentation.ui.feed.FeedItemScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.domain.repository.GlimonRepository
import javax.inject.Inject

class FeedItemViewModel @Inject constructor(
    context: Context,
    private val roomRepository: GlimonRepository,
) : ViewModel() {

    fun insertCoupon(feedItem: FeedItem, userId:String){
        viewModelScope.launch {

          TODO() //  roomRepository.insertCoupon(feedItem,userId)
        }
    }
    fun insertReview(feedItem: FeedItem, userId:String,review:String){
        viewModelScope.launch {
           // roomRepository.insertReview(feedItem, userId,review)
        }
    }
}