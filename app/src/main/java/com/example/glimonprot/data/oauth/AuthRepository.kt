package com.example.glimonprot.data.oauth

import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import stud.gilmon.data.oauth.TokenStorage
import timber.log.Timber


class AuthRepository()  {
    private val appAuth = AppAuth()

    fun corruptAccessToken() {
        TokenStorage.accessToken = "fake token"
    }

    fun logout() {
        TokenStorage.accessToken = null
        TokenStorage.refreshToken = null
        TokenStorage.idToken = null
    }

    fun getAuthRequest(config: AuthConfig): AuthorizationRequest {
        appAuth.setServiceConfiguration(config)
        return appAuth.getAuthRequest(config)
    }

//    fun getEndSessionRequest(): EndSessionRequest {
//        return appAuth.getEndSessionRequest()
//    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        config: AuthConfig
    ) {
        val tokens = appAuth.performTokenRequestSuspend(authService, tokenRequest,config)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        TokenStorage.idToken = tokens.idToken
        Timber.tag("Oauth").d("6. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
    }
}
