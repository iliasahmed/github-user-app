package com.iliasahmed.profile

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val profileScreenRoute = "profileScreenRoute/{username}"

fun profileScreenRouteWithArg(username: String) = "profileScreenRoute/$username"

fun NavController.navigateToProfileScreen(username: String) {
    val encodedUsername = Uri.encode(username) // Encode special characters
    navigate(profileScreenRouteWithArg(encodedUsername))
}


fun NavGraphBuilder.profileScreen(
    onBackBtnClick: () -> Unit
) {
    composable(
        route = profileScreenRoute,
        arguments = listOf(navArgument("username") { type = NavType.StringType })
    ) { backStackEntry ->
        val username = backStackEntry.arguments?.getString("username") ?: ""
        ProfileScreenRoute(
            userName = username,
            onBackBtnClick = onBackBtnClick
        )
    }
}