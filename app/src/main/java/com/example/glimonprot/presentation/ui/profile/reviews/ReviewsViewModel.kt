package stud.gilmon.presentation.ui.profile.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.model.ReviewItem
import stud.gilmon.domain.RoomRepository
import stud.gilmon.presentation.ui.profile.coupons.CouponsScreenState
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(
    val roomRepository: RoomRepository,
    val userId: String
) : ViewModel()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    val screenState = getReviews(userId).map { ReviewsScreenState.Reviews(reviews = it) }

    private fun getReviews(userId:String): StateFlow<List<ReviewItem>> = flow {
        val reviews = roomRepository.getReviews(userId)
        emit(reviews)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
    fun deleteReview(reviewItem: ReviewItem,userId: String){
        viewModelScope.launch {
            roomRepository.deleteReview(reviewItem, userId)
        }
    }
}