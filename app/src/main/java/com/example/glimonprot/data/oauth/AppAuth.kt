package com.example.glimonprot.data.oauth

import android.net.Uri
import androidx.core.net.toUri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import stud.gilmon.data.oauth.TokensModel
import kotlin.coroutines.suspendCoroutine

class AppAuth {
    fun setServiceConfiguration(config: AuthConfig){
        serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(config.AUTH_URI),
            Uri.parse(config.TOKEN_URI),
            null, // registration endpoint
            Uri.parse(config.END_SESSION_URI)
        )
    }
    lateinit var serviceConfiguration:AuthorizationServiceConfiguration

    fun getAuthRequest(config: AuthConfig): AuthorizationRequest {
        val redirectUri = config.CALLBACK_URL.toUri()

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            config.CLIENT_ID,
            config.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(config.SCOPE)
            .build()
    }

//    fun getEndSessionRequest(): EndSessionRequest {
//        return EndSessionRequest.Builder(serviceConfiguration)
//            .setPostLogoutRedirectUri(AuthConfig.LOGOUT_CALLBACK_URL.toUri())
//            .build()
//    }

//    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
//        return TokenRequest.Builder(
//            serviceConfiguration,
//            AuthConfig.CLIENT_ID
//        )
//            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
//            .setScopes(AuthConfig.SCOPE)
//            .setRefreshToken(refreshToken)
//            .build()
//    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        config: AuthConfig
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication(config)
            ) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }

                    else -> error("unreachable")
                }
            }
        }
    }

    private fun getClientAuthentication(config: AuthConfig): ClientAuthentication {
        return ClientSecretPost(config.CLIENT_SECRET)
    }


}


object InitialConfig : AuthConfig {
    override val login = ""
    override val AUTH_URI = ""
    override val TOKEN_URI = ""
    override val END_SESSION_URI = ""
    override val RESPONSE_TYPE = ""
    override val SCOPE = ""

    override val CLIENT_ID = ""
    override val CLIENT_SECRET = ""
    override val CALLBACK_URL = ""
    override val LOGOUT_CALLBACK_URL = ""
}

interface AuthConfig {
    val login: String
    val AUTH_URI: String
    val TOKEN_URI: String
    val END_SESSION_URI: String
    val RESPONSE_TYPE: String
    val SCOPE: String

    val CLIENT_ID: String
    val CLIENT_SECRET: String
    val CALLBACK_URL: String
    val LOGOUT_CALLBACK_URL: String
}
object GithubAuthConfig : AuthConfig {
    override val login = "github.com"
    override val AUTH_URI = "https://github.com/login/oauth/authorize"
    override val TOKEN_URI = "https://github.com/login/oauth/access_token"
    override val END_SESSION_URI = "https://github.com/logout"
    override val RESPONSE_TYPE = ResponseTypeValues.CODE
    override val SCOPE = "user,repo"

    override val CLIENT_ID = "Ov23liljs2aNTHaAV38p"
    override val CLIENT_SECRET = "7e7c7b2f908111dfe663bf63b6bd027cfd9cfc01"
    override val CALLBACK_URL = "com.example.glimonprot://github.com/callback"
    override val LOGOUT_CALLBACK_URL = "stud.oauth://github.com/logout_callback"
}
object MailAuthConfig : AuthConfig {
    override val login = "mail.ru"
    override val AUTH_URI = "https://oauth.mail.ru/login"
    override val TOKEN_URI = "https://oauth.mail.ru/token"
    override val END_SESSION_URI = ""
    override val RESPONSE_TYPE = ResponseTypeValues.CODE
    override val SCOPE = "userinfo"

    override val CLIENT_ID = "db383245236e4f618ac6aa578fbce8fc"
    override val CLIENT_SECRET = "f99a06614ef7490fac006282ceb12997"
    override val CALLBACK_URL = "stud.oauth://mail.ru/callback"
    override val LOGOUT_CALLBACK_URL = ""
}