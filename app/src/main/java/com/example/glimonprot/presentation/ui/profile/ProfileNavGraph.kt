package com.example.glimonprot.presentation.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.glimonprot.di.viewModelFactory.ViewModelFactory
import com.example.glimonprot.presentation.ui.Screen
import com.example.glimonprot.presentation.ui.profile.coupons.CouponsProfile
import com.example.glimonprot.presentation.ui.profile.reviews.ReviewsProfile
import com.example.glimonprot.presentation.ui.profile.settings.SettingsProfile
import stud.gilmon.data.local.entities.UsersEntity

@Composable
fun ProfileNavGraph(
    darkTheme: Boolean,
    user: UsersEntity,
    lazyListStateList: List<LazyListState>,
    navController: NavHostController,
    paddingValues: PaddingValues,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory,
    onClick: () -> Unit
) {

    NavHost(
        navController = navController,
        route = Screen.Profile.route,
        startDestination = ProfileDestinations.CouponsProfile.route
    )
    {
        composable(route = ProfileDestinations.CouponsProfile.route) {
            CouponsProfile(user.userId,lazyListStateList[0],viewModelFactory)
        }
        composable(route = ProfileDestinations.ReviewsProfile.route) {
            ReviewsProfile(user.userId,lazyListStateList[0],viewModelFactory)
        }
        composable(route = ProfileDestinations.SettingsProfile.route) {
            SettingsProfile(
                darkTheme,
               // navController = navController,
                user = user,
                lazyListState = lazyListStateList[2],
                toggleTheme = toggleTheme,
                viewModelFactory = viewModelFactory,
                onClick = onClick
            )
        }
    }
}


sealed class ProfileDestinations(val route: String) {
    object CouponsProfile : ProfileDestinations(route = "profile_coupons")
    object SettingsProfile : ProfileDestinations(route = "profile_settings")
    object ReviewsProfile : ProfileDestinations(route = "profile_reviews")
}
