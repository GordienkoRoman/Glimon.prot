package com.example.glimonprot.presentation.ui.profile.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.glimonprot.domain.model.ReviewItem
import com.example.glimonprot.domain.repository.GlimonRepository
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(
    val glimonRepository: GlimonRepository,
    val userId: String
) : ViewModel()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    val screenState = getReviews(userId).map { ReviewsScreenState.Reviews(reviews = it) }

    private fun getReviews(userId:String): StateFlow<List<ReviewItem>> = flow {
        val reviews = glimonRepository.getReviews(userId)
        emit(reviews)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )
    fun deleteReview(reviewItem: ReviewItem,userId: String){
        viewModelScope.launch {
            glimonRepository.deleteReview(reviewItem, userId)
        }
    }
}