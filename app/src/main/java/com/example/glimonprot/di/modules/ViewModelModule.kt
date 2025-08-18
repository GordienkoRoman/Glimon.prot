package com.example.glimonprot.di.modules

import androidx.lifecycle.ViewModel
import com.example.glimonprot.MainViewModel
import com.example.glimonprot.di.viewModelFactory.ViewModelKey
import com.example.glimonprot.presentation.ui.feed.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import example.glimonprot.presentation.ui.feed.FeedItemScreen.FeedItemViewModel
import example.glimonprot.presentation.ui.login.LoginViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedItemViewModel::class)
    fun bindFeedItemViewModel(viewModel: FeedItemViewModel): ViewModel


}