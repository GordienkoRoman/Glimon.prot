package com.example.glimonprot.di.components

import com.example.glimonprot.di.modules.ProfileViewModelModule
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ProfileViewModelModule::class]
)
interface ProfileScreenComponent {

    fun getViewModelFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance userId:String
        ) :ProfileScreenComponent
    }
}