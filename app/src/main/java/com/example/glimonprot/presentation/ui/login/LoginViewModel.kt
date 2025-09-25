package example.glimonprot.presentation.ui.login

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.datastore.core.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import com.example.glimonprot.R
import com.example.glimonprot.data.oauth.AuthConfig
import com.example.glimonprot.data.oauth.AuthRepository
import com.example.glimonprot.data.oauth.GithubAuthConfig
import com.example.glimonprot.data.oauth.InitialConfig
import com.example.glimonprot.data.oauth.MailAuthConfig
import com.example.glimonprot.data.remote.RemoteUser
import com.example.glimonprot.data.remote.userApi.GithubApi
import com.example.glimonprot.data.remote.userApi.MailApi
import com.example.glimonprot.domain.repository.GlimonRepository
import stud.gilmon.data.local.entities.UsersEntity
import timber.log.Timber
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val glimonRepository: GlimonRepository,
    private val mailApi: MailApi,
    private val githubApi: GithubApi,
    context: Context
) : ViewModel() {

    private val remoteUserGithubInfoMutableStateFlow =
        MutableStateFlow<RemoteUser.RemoteGithubUser?>(null)
    private val remoteUserMailInfoMutableStateFlow =
        MutableStateFlow<RemoteUser.RemoteMailUser?>(null)
    var config: AuthConfig = InitialConfig
    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(context)

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val remoteUserGithubInfoFlow: Flow<RemoteUser.RemoteGithubUser?>
        get() = remoteUserGithubInfoMutableStateFlow.asStateFlow()

    val remoteUserMailInfoFlow: Flow<RemoteUser.RemoteMailUser?>
        get() = remoteUserMailInfoMutableStateFlow.asStateFlow()

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()


    private val _userFlow  = MutableStateFlow(UsersEntity(userId = ""))
    val userFlow
        get() = _userFlow.asStateFlow()

    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()

    fun onAuthCodeFailed() {
        toastEventChannel.trySendBlocking(R.string.auth_canceled)
    }

    private fun setUser(login: String) {
        viewModelScope.launch {
            glimonRepository.setPrefUser(login)
        }
        loadUserInfo(login)
    }

    fun getUser(login: String) {
        viewModelScope.launch {
            _userFlow.value = glimonRepository.getUser(login)?: UsersEntity(userId = "")
        }
    }

    private fun loadUserInfo(login: String) {
        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                when (login) {
                    GithubAuthConfig.login -> githubApi.getCurrentUser()
                    MailAuthConfig.login -> {

                        //mailApi.getCurrentUser(TokenStorage.accessToken!!)
                    }

                    else -> throw Exception("MM?")
                }
            }.onSuccess {
                when (login) {
                    GithubAuthConfig.login -> {
                        remoteUserGithubInfoMutableStateFlow.value =
                            it as RemoteUser.RemoteGithubUser
                        insertItem(it)
                    }

                    MailAuthConfig.login -> {
                        glimonRepository.upsertUser(
                            UsersEntity(reviewId = 1, userId = "mail.ru")
                        )
                        authSuccessEventChannel.send(Unit)
//                    remoteUserMailInfoMutableStateFlow.value = it as RemoteUser.RemoteMailUser
                        //     insertItem(it as RemoteUser)
                    }
                }
                loadingMutableStateFlow.value = false
            }.onFailure {
                Timber.tag("Oauth").d(it)
                loadingMutableStateFlow.value = false
                remoteUserGithubInfoMutableStateFlow.value = null
                toastEventChannel.trySendBlocking(R.string.get_user_error)
            }
        }
    }


    private fun insertItem(user: RemoteUser.RemoteGithubUser) {

        when (config) {
            GithubAuthConfig -> {
                val it = user
                viewModelScope.launch {
                    try {
                        glimonRepository.upsertUser(
                            UsersEntity(
                                firstName = it.name ?: "",
                                aboutMe = it.bio ?: "",
                                mail = it.email ?: "",
                                avatarUrl = it.avatar_url ?: "",
                                reviewId = 2,
                                userId = config.login
                            )
                        )
                        authSuccessEventChannel.send(Unit)
                    } catch (e: IOException) {
                        Timber.tag("ERROR $e")
                    }
                }
            }

            MailAuthConfig -> {
                val it = user as RemoteUser.RemoteMailUser
                viewModelScope.launch {
                    try {
                        glimonRepository.upsertUser(
//                        UsersEntity(
//                            it.first_name ?: "",
//                            it.last_name ?: "",
//                            it.sex.toString(),
//                            it.birthday ?: "",
//                            "family",
//                            "about",
//                            reviewId = 3,
//                            userId = "mail.ru"
//                        )
                            UsersEntity(reviewId = 1, userId = "mail.ru")
                        )
                    } catch (e: IOException) {
                        Timber.tag("ERROR $e")
                    }
                }
            }
        }


    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {

        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                Timber.tag("Oauth")
                    .d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest,
                    config
                )
            }.onSuccess {
                loadingMutableStateFlow.value = false
                setUser(config.login)

            }.onFailure {
                loadingMutableStateFlow.value = false
                toastEventChannel.send(R.string.auth_canceled)
            }
        }
    }

    fun openLoginPage(login: String) {
        if (_userFlow.value.userId != "") {
            viewModelScope.launch {
                glimonRepository.setPrefUser(login)
                authSuccessEventChannel.send(Unit)
            }
        } else {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            val authRequest = authRepository.getAuthRequest(config)

            Timber.tag("Oauth")
                .d("1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")

            val openAuthPageIntent = authService.getAuthorizationRequestIntent(
                authRequest,
                customTabsIntent
            )

            openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
            Timber.tag("Oauth").d("2. Open auth page: ${authRequest.toUri()}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}