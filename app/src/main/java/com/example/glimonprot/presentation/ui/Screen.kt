package stud.gilmon.presentation.ui


sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Feed : Screen(route = "feed_screen")
    object FeedMain : Screen(route = "feed_main_screen")
    object FeedSearch : Screen(route = "feed_search_screen")
    object FeedItem : Screen(route = "feed_item_screen")
    object Login : Screen(route = "login_screen")
    object Support : Screen(route = "support_screen")
    object ContactSupport : Screen(route = "contact_support_screen")
    object SupportMain : Screen(route = "support_main_screen")
    object Profile : Screen(route = "profile_screen")
    object Friends : Screen(route = "friends_screen")

    companion object {
        const val KEY_AUTH_USER = "auth_user"
        const val KEY_FEED_ITEM_INDEX = "feed_item_index"
        const val KEY_FEED_SEARCH_INDEX = "feed_search_index"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
