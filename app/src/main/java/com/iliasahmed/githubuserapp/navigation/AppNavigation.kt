package com.iliasahmed.githubuserapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.iliasahmed.profile.navigateToProfileScreen
import com.iliasahmed.profile.profileScreen
import com.iliasahmed.userslist.usersListScreen
import com.iliasahmed.userslist.usersListScreenRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = usersListScreenRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        usersListScreen(
            onUserItemClick = { username ->
                navController.navigateToProfileScreen(username)
            }
        )
        profileScreen(onBackBtnClick = navController::popBackStackOrIgnore)
    }
}

fun NavController.popBackStackOrIgnore() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}