package com.example.glimonprot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.glimonprot.ui.theme.GlimonprotTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {


//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory
//    private val component by lazy {
//        (application as BaseApplication).component
//    }


//    private val viewModel by viewModels<MainViewModel> {
//        viewModelFactory
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   component.inject(this)
        super.onCreate(savedInstanceState)

//        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                viewModel.loadingFlow.value
//            }
//        }
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setContent {
//            LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//            val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
//            viewModel.readFromDataStore.observe(this) {
//               viewModel.getUser(it.toString())
//                //login.value = it.toString()
//               // user.value = viewModel.userFlow.value ?: user.value.copy()
//            }
//
//
//            val photos = remember { mutableStateOf(listOf(UnsplashDto())) }
//            SideEffect {
//                viewModel.remoteRandomPhotosStateFlow.launchAndCollectIn(lifecycleOwner.value) {
//                    if (it != null)
//                        photos.value = it
//                }
//            }
//            var darkTheme by remember { mutableStateOf(true) }
//
//            GilmonTheme(darkTheme) {
//
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .windowInsetsPadding(WindowInsets.safeDrawing),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val feedItems = viewModel.feedItems.collectAsLazyPagingItems()
//                    val user = remember {
//                        mutableStateOf(viewModel.userFlow)
//                    }
//                  //  Test()
//                    MainScreen(
//                        darkTheme,
//                        feedItems,
//                        user.value,
//                        toggleTheme = { darkTheme = !darkTheme },
//                        viewModelFactory = viewModelFactory
//                    )
//                }
//            }
//        }
    }
//}
//@Composable
//fun LockScreenOrientation(orientation: Int) {
//    val context = LocalContext.current
//    DisposableEffect(Unit) {
//        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
//        val originalOrientation = activity.requestedOrientation
//        activity.requestedOrientation = orientation
//        onDispose {
//            // restore original orientation when view disappears
//            activity.requestedOrientation = originalOrientation
//        }
//    }
//}
//
//fun Context.findActivity(): Activity? = when (this) {
//    is Activity -> this
//    is ContextWrapper -> baseContext.findActivity()
//    else -> null
//}
//
//@Composable
//fun Test() {
//
//}
//@Composable
//fun lazycolumn() {
//    LazyColumn(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(
//                bottom = 75.dp
//            ),
//        verticalArrangement = Arrangement.spacedBy(15.dp)
//    ) {
//        item{
//            Box(
//                modifier = Modifier
//                    .background(Color.Black)
//                    .fillMaxWidth()
//                    .height(100.dp)
//            )
//        }
//    }
}