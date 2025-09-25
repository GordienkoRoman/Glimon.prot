package com.example.glimonprot.presentation.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.BaseApplication
import com.example.glimonprot.R
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import stud.gilmon.data.local.entities.UsersEntity

@Composable
fun ProfileScreen(
    darkTheme: Boolean,
    user: UsersEntity,
    navController: NavHostController = rememberNavController(),
    toggleTheme: () -> Unit,
    onClick: () -> Unit
) {
    val component =
        (LocalContext.current.applicationContext as BaseApplication).component
            .profileScreenComponentFactory()
            .create(user.userId)
    val viewModelFactory = component.getViewModelFactory()
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val lazyListStateList: List<LazyListState> = listOf(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    //val  user = viewModel.getUser(login)
    MainContent(
        darkTheme = darkTheme,
        user = user,
        navController = navController,
        toggleTheme = { toggleTheme() },
        viewModelFactory = viewModelFactory,
        viewModel = viewModel,
        lazyListStateList,
        onClick = onClick
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    darkTheme: Boolean,
    user: UsersEntity,
    navController: NavHostController,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory,
    viewModel: ProfileViewModel,
    lazyListStateList: List<LazyListState>,
    onClick: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollBehavior2 = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    val topPadding by animateDpAsState(
//        targetValue = if (lazyListStateList[0].isScrolled ||
//            lazyListStateList[1].isScrolled ||
//            lazyListStateList[2].isScrolled
//        ) 0.dp else TOP_BAR_HEIGHT,
//        animationSpec = tween(durationMillis = 300), label = ""
//    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior2.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(), title = {
                    ProfileTopBar(
                        //  lazyListStateList = lazyListStateList,
                        user,
                        viewModel
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onBackground),
                scrollBehavior = scrollBehavior2
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Scaffold(
                    topBar = { ProfileTopNavigationBar(navController = navController) },
                    contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
                ) {
                    ProfileNavGraph(
                        darkTheme,
                        user = user,
                        lazyListStateList,
                        navController = navController,
                        paddingValues = it,
                        toggleTheme = toggleTheme,
                        viewModelFactory = viewModelFactory,
                        onClick
                    )
                }

            }
        }
    )
}

@Composable
fun ProfileTopNavigationBar(navController: NavController) {
    var selectedTab by remember { mutableStateOf(R.string.profile_coupons) }
    val tabs = ProfileTab.values()
    // val navBackStackEntry by navController.currentBackStackEntryAsState()
    //  val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        Modifier.height(TOP_NAVIGATION_BAR_HEICHT),
        containerColor = MaterialTheme.colorScheme.onBackground,
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onBackground,
                ),
                icon = {},
                label = {
                    Text(
                        text = stringResource(tab.title),
                        color = if (tab.title == selectedTab) MaterialTheme.colorScheme.tertiary else Color.White
                    )
                },
                selected = tab.title == selectedTab,
                onClick = {
                    selectedTab = tab.title
                    val route = when (tab) {
                        ProfileTab.SETTINGS -> ProfileDestinations.SettingsProfile.route
                        ProfileTab.COUPONS -> ProfileDestinations.CouponsProfile.route
                        ProfileTab.REVIEWS -> ProfileDestinations.ReviewsProfile.route
                    }
                    navController.navigate(route = route) {
                        launchSingleTop = true
                        popUpTo(ProfileDestinations.CouponsProfile.route) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    user: UsersEntity,
    viewModel: ProfileViewModel
) {

    val selectedImageUri = rememberSaveable { mutableStateOf("") }
    val painter =
        rememberAsyncImagePainter(model = selectedImageUri.value.ifEmpty { R.drawable.img })

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri.toString()
            //  user.value = user.value.copy(avatarUrl = uri.toString())
            viewModel.updateUserData(user.copy(avatarUrl = uri.toString()))
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(top = 15.dp)
            .height(
                TOP_BAR_HEIGHT
            )
    )
    {
        Row()
        {
            Column( modifier = Modifier
                .weight(1f)) {
                Text(text = "Hi,", color = TextFieldLabelColor)
           //     CustomText(text = "Hi,", textColor = TextFieldLabelColor)
                Text(
                    "${user.firstName} ${user.lastName}",
                    fontSize = 25.sp,
                    color = Color.White,

                )
            }
            Image(painter = painter,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ),
                contentDescription = "god_beauty",
                contentScale = ContentScale.Crop)
        }

    }
}

val TOP_BAR_HEIGHT = 80.dp
val TOP_NAVIGATION_BAR_HEICHT = 50.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

enum class ProfileTab(
    @StringRes val title: Int,
) {
    COUPONS(R.string.profile_coupons),
    REVIEWS(R.string.profile_reviews),
    SETTINGS(R.string.profile_settings);
}