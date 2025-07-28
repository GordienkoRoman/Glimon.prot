package stud.gilmon.presentation.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import stud.gilmon.di.viewModelFactory.ViewModelFactory
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.presentation.ui.Screen
import stud.gilmon.presentation.ui.profile.coupons.CouponsProfile
import stud.gilmon.presentation.ui.profile.reviews.ReviewsProfile
import stud.gilmon.presentation.ui.profile.settings.SettingsProfile

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
