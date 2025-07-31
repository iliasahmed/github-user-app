package com.iliasahmed.userslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iliasahmed.designsystem.component.ScaffoldTopAppbarWithSearch
import com.iliasahmed.designsystem.theme.color
import com.iliasahmed.entity.UserEntity
import com.iliasahmed.ui.component.NetworkErrorMessage

@Composable
internal fun UserListRoute(
    viewModel: UsersListViewModel = hiltViewModel(),
    onUserItemClick: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }
    val userListUiSate by viewModel.usersListUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    UsersListScreen(
        userListUiSate = userListUiSate,
        onUserItemClick = onUserItemClick,
        onRefreshUserList = viewModel::handleAction,
        query = searchQuery,
        onQueryChange = { viewModel.fetchUsers(it) },
        onClearQuery = { viewModel.fetchUsers("") }
    )
}

@Composable
fun UsersListScreen(
    userListUiSate: UsersListUiState,
    onUserItemClick: (String) -> Unit,
    onRefreshUserList: (UsersListUiAction) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    ScaffoldTopAppbarWithSearch(
        title = "User List",
        containerColor = MaterialTheme.color.secondaryBackground,
        query = query,
        onQueryChange = onQueryChange,
        onClearQuery = onClearQuery,
    ) {
        val modifier = Modifier.padding(it)
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (userListUiSate) {
                is UsersListUiState.Error -> {
                    NetworkErrorMessage(
                        message = userListUiSate.message,
                        onClickRefresh = {
                            onRefreshUserList(UsersListUiAction.FetchUsersList)
                        }
                    )
                }

                is UsersListUiState.HasUsersList -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            items(userListUiSate.usersList) { userItem ->
                                UserListItem(
                                    userItem = userItem,
                                    onItemClick = onUserItemClick
                                )
                            }
                        }
                    }
                }

                UsersListUiState.Loading -> CircularProgressIndicator(modifier = Modifier.testTag("loadingIndicator"))
                UsersListUiState.UsersListEmpty -> Text(text = "No User List Found")
            }
        }
    }
}

@Composable
private fun UserListItem(
    modifier: Modifier = Modifier,
    userItem: UserEntity,
    onItemClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onItemClick(userItem.userName) },
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.color.white)
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = userItem.userAvatar),
                    contentDescription = "",
                    modifier = modifier
                        .size(60.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
                Spacer(modifier = modifier.width(16.dp))
                Column {
                    Text(text = userItem.userName, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = modifier.height(4.dp))
                    Text(text = "ID: ${userItem.id}", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.ChevronRight, "right-arrow")
            }
        }
    }
}



