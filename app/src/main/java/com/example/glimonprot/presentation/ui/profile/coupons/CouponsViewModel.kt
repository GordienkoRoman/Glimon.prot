package stud.gilmon.presentation.ui.profile.coupons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import stud.gilmon.data.model.FeedItem
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject


class CouponsViewModel @Inject constructor(
    val roomRepository: RoomRepository,
    val userId: String
) : ViewModel()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
     val screenState = getCoupons(userId).map { CouponsScreenState.Coupons(coupons = it) }

     private fun getCoupons(userId:String): StateFlow<List<FeedItem>> = flow {
        val coupons = roomRepository.getCoupons(userId)
         emit(coupons)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    fun deleteCoupon(feedItem: FeedItem,userId: String){
        viewModelScope.launch {
            roomRepository.deleteCoupon(feedItem, userId)
        }
    }
    var couponsStatus : Int = 0
    init {
        coroutineScope.launch {
        }
    }

}