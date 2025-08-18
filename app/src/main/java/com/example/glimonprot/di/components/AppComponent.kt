package com.example.glimonprot.di.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.glimonprot.MainActivity
import com.example.glimonprot.data.remote.UnsplashApi
import com.example.glimonprot.data.remote.network.AuthorizationInterceptor
import com.example.glimonprot.data.remote.userApi.GithubApi
import com.example.glimonprot.data.remote.userApi.MailApi
import com.example.glimonprot.di.modules.AppModule
import com.example.glimonprot.di.modules.DataBaseModule
import com.example.glimonprot.di.modules.DataStoreModule
import com.example.glimonprot.di.modules.DispatcherModule
import com.example.glimonprot.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(
    modules =  [AppModule::class,
        ViewModelModule::class,
        DataBaseModule::class,
        DataStoreModule::class,
        DispatcherModule::class]
)
interface  AppComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    fun inject(mainActivity: MainActivity)

   // fun profileScreenComponentFactory():ProfileScreenComponent.Factory
    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}

