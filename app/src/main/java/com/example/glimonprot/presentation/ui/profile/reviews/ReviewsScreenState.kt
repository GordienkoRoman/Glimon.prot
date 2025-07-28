package stud.gilmon.presentation.ui.profile.reviews

import stud.gilmon.data.model.ReviewItem

sealed class ReviewsScreenState {

    object Initial : ReviewsScreenState()

    object Loading : ReviewsScreenState()

    data class Reviews(
        val reviews: List<ReviewItem>
    ) : ReviewsScreenState()
}
