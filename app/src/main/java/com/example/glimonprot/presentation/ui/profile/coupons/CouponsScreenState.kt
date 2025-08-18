package com.example.glimonprot.presentation.ui.profile.coupons

import com.example.glimonprot.domain.model.FeedItem

sealed class CouponsScreenState {

    object Initial : CouponsScreenState()

    object Loading : CouponsScreenState()

    data class Coupons(
        val coupons: List<FeedItem>
    ) : CouponsScreenState()
}
