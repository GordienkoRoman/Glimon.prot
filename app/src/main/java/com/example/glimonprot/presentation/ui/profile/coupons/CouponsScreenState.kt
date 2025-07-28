package stud.gilmon.presentation.ui.profile.coupons

import stud.gilmon.data.model.FeedItem
import stud.gilmon.presentation.ui.Screen

sealed class CouponsScreenState {

    object Initial : CouponsScreenState()

    object Loading : CouponsScreenState()

    data class Coupons(
        val coupons: List<FeedItem>
    ) : CouponsScreenState()
}
