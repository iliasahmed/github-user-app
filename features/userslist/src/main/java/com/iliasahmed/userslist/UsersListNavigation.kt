package com.iliasahmed.userslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val usersListScreenRoute = "usersListScreenRoute"

fun NavGraphBuilder.usersListScreen(
    onUserItemClick: (String) -> Unit
) {
    composable(route = usersListScreenRoute) {
        UserListRoute(
            onUserItemClick = onUserItemClick
        )
    }
}