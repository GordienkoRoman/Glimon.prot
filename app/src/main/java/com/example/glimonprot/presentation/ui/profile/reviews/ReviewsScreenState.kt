package com.example.glimonprot.presentation.ui.profile.reviews

import com.example.glimonprot.domain.model.ReviewItem

sealed class ReviewsScreenState {

    object Initial : ReviewsScreenState()

    object Loading : ReviewsScreenState()

    data class Reviews(
        val reviews: List<ReviewItem>
    ) : ReviewsScreenState()
}
