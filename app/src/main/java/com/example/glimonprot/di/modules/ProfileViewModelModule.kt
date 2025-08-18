package com.example.glimonprot.di.modules

import androidx.lifecycle.ViewModel
import com.example.glimonprot.di.viewModelFactory.ViewModelKey
import com.example.glimonprot.presentation.ui.profile.ProfileViewModel
import com.example.glimonprot.presentation.ui.profile.coupons.CouponsViewModel
import com.example.glimonprot.presentation.ui.profile.reviews.ReviewsViewModel
import com.example.glimonprot.presentation.ui.profile.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CouponsViewModel::class)
    fun bindCouponsViewModel(viewModel: CouponsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel::class)
    fun bindReviewsViewModel(viewModel: ReviewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}