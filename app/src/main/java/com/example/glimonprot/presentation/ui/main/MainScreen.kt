package com.example.glimonprot.presentation.ui.main

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.glimonprot.R
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.components.CustomDragHandle
import com.example.glimonprot.presentation.components.CustomNavigationBar
import com.example.glimonprot.presentation.theme.TextFieldLabelColor
import com.example.glimonprot.presentation.ui.login.LoginScreen
import com.example.glimonprot.presentation.ui.Screen
import com.example.glimonprot.presentation.ui.feed.FeedItemComponent
import stud.gilmon.data.local.entities.UsersEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    darkTheme: Boolean,
    feedItems:LazyPagingItems<FeedItem>,
    user: StateFlow<UsersEntity>,
    navController: NavHostController = rememberNavController(),
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()


    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
        items(count = feedItems.itemCount) { index ->
            val item = feedItems[index]
            FeedItemComponent(item!!)
        }
    }
    BottomSheetScaffold(
        sheetDragHandle = { CustomDragHandle() },
        containerColor = MaterialTheme.colorScheme.onBackground,
        sheetContainerColor = MaterialTheme.colorScheme.onBackground,
        sheetContent = {
            LoginScreen(navController, viewModelFactory = viewModelFactory) {
                scope.launch {
                    if (scaffoldState.bottomSheetState.isVisible) {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Scaffold(
            bottomBar = {
                MainBottomAppBar(
                    navController = navController,
                    user,
                    scaffoldState = scaffoldState
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) {
            MainScreenNavGraph(
                darkTheme,
                feedItems,
                navController,
                it,
                user,
                toggleTheme = toggleTheme,
                viewModelFactory = viewModelFactory
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomAppBar(
    navController: NavController,
    user: StateFlow<UsersEntity>,
    scaffoldState: BottomSheetScaffoldState
) {
    var iconColor: MutableState<Color> = remember {
        mutableStateOf(TextFieldLabelColor)
    }
    val tabs = MainTab.values()
    var selectedTab by remember { mutableIntStateOf(tabs[0].title) }
    val scope = rememberCoroutineScope()
    CustomNavigationBar{
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(tab.icon), contentDescription = null, tint = if (tab.title == selectedTab) MaterialTheme.colorScheme.tertiary else TextFieldLabelColor) },
                colors = NavigationBarItemDefaults.colors(
                   indicatorColor =  MaterialTheme.colorScheme.onBackground,
                ),
                selected = tab.title == selectedTab,
                onClick = {
                    selectedTab = tab.title
                    val route: String = when (tab) {
                        MainTab.SUPPORT -> Screen.SupportMain.route
                        MainTab.FEED -> Screen.FeedMain.route
                        MainTab.FRIENDS->Screen.SupportMain.route
                        MainTab.PROFILE -> {
                            if (user.value.userId == "") {
                                Screen.Profile.route + "/"
                            } else {
                                val userJson = Gson().toJson(user.value)
                                Screen.Profile.route + "/" + Uri.encode(userJson)
                            }

                        }
                    }
                    if (route == Screen.Profile.route + "/") {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    } else {
                        navController.navigate(route = route)
                    }
                },
            )
        }
    }
}


enum class MainTab(
    @StringRes val title: Int,
    val icon: Int
) {
    FEED(R.string.main_feed, R.drawable.baseline_search_24),
    SUPPORT(R.string.main_support, R.drawable.baseline_help_24),
    PROFILE(R.string.main_profile, R.drawable.baseline_person_24),
    FRIENDS(R.string.main_friends, R.drawable.baseline_people_24);

    companion object {
        fun getTabFromResource(@StringRes resource: Int): MainTab {
            return when (resource) {
                R.string.main_feed -> FEED
                R.string.main_support -> SUPPORT
                R.string.main_profile -> PROFILE
                else -> PROFILE
            }
        }
    }
}