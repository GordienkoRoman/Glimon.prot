package com.example.glimonprot.di.modules

import android.content.Context
import com.example.glimonprot.data.remote.UnsplashApi
import com.example.glimonprot.data.remote.network.AuthorizationInterceptor
import com.example.glimonprot.data.remote.userApi.GithubApi
import com.example.glimonprot.data.remote.userApi.MailApi
import com.example.glimonprot.di.components.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideOkhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Timber.tag("Network").d(it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(AuthorizationInterceptor())
            .build()
    }

    @AppScope
    @Provides
    fun provideGithubApiFactService(okHttpClient: OkHttpClient): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(GithubApi::class.java)
    }

    @AppScope
    @Provides
    fun provideMailApiFactService(okHttpClient: OkHttpClient): MailApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.appsmail.ru/platform/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(MailApi::class.java)
    }
    @AppScope
    @Provides
    fun provideUnsplashImagesService(okHttpClient: OkHttpClient): UnsplashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(UnsplashApi::class.java)
    }

}

