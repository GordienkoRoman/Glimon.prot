package com.example.glimonprot

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.glimonprot.data.remote.UnsplashDto
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.presentation.ui.main.MainScreen
import com.example.glimonprot.ui.theme.GlimonprotTheme
import stud.gilmon.base.utils.launchAndCollectIn
import javax.inject.Inject
import kotlin.getValue

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as BaseApplication).component
    }


    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loadingFlow.value
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
            viewModel.readFromDataStore.observe(this) {
               viewModel.getUser(it.toString())
                //login.value = it.toString()
               // user.value = viewModel.userFlow.value ?: user.value.copy()
            }


            val photos = remember { mutableStateOf(listOf(UnsplashDto())) }
            SideEffect {
                viewModel.remoteRandomPhotosStateFlow.launchAndCollectIn(lifecycleOwner.value) {
                    if (it != null)
                        photos.value = it
                }
            }
            var darkTheme by remember { mutableStateOf(true) }

            GlimonprotTheme(darkTheme) {

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val feedItems = viewModel.feedItems.collectAsLazyPagingItems()
                    val user = remember {
                        mutableStateOf(viewModel.userFlow)
                    }
                    MainScreen(
                        darkTheme,
                        feedItems,
                        user.value,
                        toggleTheme = { darkTheme = !darkTheme },
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
