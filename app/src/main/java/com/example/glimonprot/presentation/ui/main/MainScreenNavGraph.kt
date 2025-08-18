package com.example.glimonprot.presentation.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.paging.compose.LazyPagingItems
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.StateFlow
import com.example.glimonprot.domain.model.FeedItem
import com.example.glimonprot.presentation.ui.Screen
import com.example.glimonprot.presentation.ui.Screen.Companion.KEY_AUTH_USER
import com.example.glimonprot.presentation.ui.Screen.Companion.KEY_FEED_ITEM_INDEX
import com.example.glimonprot.presentation.ui.Screen.Companion.KEY_FEED_SEARCH_INDEX
import example.glimonprot.presentation.ui.feed.FeedItemScreen.FeedItemScreen
import com.example.glimonprot.presentation.ui.feed.FeedScreen
import example.glimonprot.presentation.ui.feed.FeedSearchScreen
import com.example.glimonprot.presentation.ui.profile.ProfileScreen
import com.example.glimonprot.presentation.ui.support.ContactSupportScreen
import com.example.glimonprot.presentation.ui.support.SupportScreen
import stud.gilmon.data.local.entities.UsersEntity

@Composable
fun MainScreenNavGraph(
    darkTheme: Boolean,
    feedItems:LazyPagingItems<FeedItem>,
    navController: NavHostController,
    paddingValues: PaddingValues,
    user: StateFlow<UsersEntity>,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Screen.Feed.route,
        route = Screen.Home.route
    ) {
        navigation(route = Screen.Feed.route,
            startDestination = Screen.FeedMain.route){
            composable(route = Screen.FeedMain.route)
            {
                FeedScreen(feedItems,viewModelFactory,
                    user,
                    onSearchClick = {navController.navigate(Screen.FeedSearch.route+'/'+it) }
                ) { navController.navigate(Screen.FeedItem.route + "/" + it) }
            }
            composable(route = Screen.FeedSearch.withArgs("{$KEY_FEED_SEARCH_INDEX}"),
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            )
            {
                val text = it.arguments?.getString(KEY_FEED_SEARCH_INDEX) ?: ""
                FeedSearchScreen(viewModelFactory,feedItems,text)
            }
            composable(route =Screen.FeedItem.withArgs("{$KEY_FEED_ITEM_INDEX}"),
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
            )
            {
                val index = it.arguments?.getString(KEY_FEED_ITEM_INDEX) ?: ""
                FeedItemScreen(user,viewModelFactory,feedItems.itemSnapshotList.items[index.toInt()])
            }
        }

        navigation(route = Screen.Support.route,
        startDestination = Screen.SupportMain.route){
            composable(route = Screen.SupportMain.route) {
                SupportScreen() {navController.navigate(Screen.ContactSupport.route) }
            }
            composable(route = Screen.ContactSupport.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                }) {
                ContactSupportScreen(user) { navController.navigate(Screen.SupportMain.route) }
            }
        }

      // composable(route = "${Screen.Profile.route}/{$KEY_AUTH_USER}") {
        composable(route = Screen.Profile.withArgs("{$KEY_AUTH_USER}")) {
            val userJson = it.arguments?.getString(KEY_AUTH_USER) ?: "404"
           // user.value = Gson().fromJson(userJson, UsersEntity::class.java)
            //val user = remember { mutableStateOf(Gson().fromJson(userJson, UsersEntity::class.java)) }
            ProfileScreen(
                darkTheme, user = Gson().fromJson(userJson, UsersEntity::class.java),
                toggleTheme = toggleTheme,
            ) { navController.navigate(Screen.FeedMain.route) }
        }
    }
}

