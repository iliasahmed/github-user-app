package com.iliasahmed.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.ForkRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.iliasahmed.designsystem.component.ScaffoldTopAppbar
import com.iliasahmed.designsystem.theme.color
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import com.iliasahmed.ui.component.NetworkErrorMessage

@Composable
internal fun ProfileScreenRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackBtnClick: () -> Unit,
    userName: String
) {
    LaunchedEffect(userName) {
        viewModel.setUserName(userName)
    }
    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()

    ProfileScreen(
        profileUiState = profileUiState,
        onRefreshProfile = viewModel::handleAction,
        onBackBtnClick = onBackBtnClick
    )
}

@Composable
private fun ProfileScreen(
    profileUiState: ProfileUiState,
    onRefreshProfile: (ProfileUiAction) -> Unit,
    onBackBtnClick: () -> Unit
) {
    ScaffoldTopAppbar(
        title = "Profile",
        containerColor = MaterialTheme.color.secondaryBackground,
        onNavigationIconClick = onBackBtnClick
    ) {
        val modifier = Modifier.padding(it)
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (profileUiState) {
                is ProfileUiState.Error -> NetworkErrorMessage(message = profileUiState.message) {
                    onRefreshProfile(ProfileUiAction.FetchProfile)
                }

                ProfileUiState.Loading -> CircularProgressIndicator()
                is ProfileUiState.Success -> {
                    ProfileContentView(profileUiState.profile, profileUiState.repositories)
                }
            }
        }
    }
}

@Composable
private fun ProfileContentView(
    profile: ProfileEntity,
    repoItems: List<RepoItemEntity>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            item {
                ProfileHeader(profile = profile, modifier = modifier)
            }
            items(repoItems) { repoItem ->
                RepoListItem(
                    repoItem = repoItem,
                )
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    profile: ProfileEntity,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = profile.userAvatar),
                contentDescription = "",
                modifier = modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = modifier.width(16.dp))
            Column(modifier = modifier.align(Alignment.CenterVertically)) {
                Text(text = profile.userName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = profile.userName)
            }
        }

        Spacer(modifier = modifier.height(16.dp))

        Row(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = profile.repoCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Repository")
            }

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = profile.followerCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Follower")
            }

            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = profile.followingCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Following")
            }
        }
        Spacer(modifier = modifier.height(20.dp))
    }
}

@Composable
private fun RepoListItem(
    modifier: Modifier = Modifier,
    repoItem: RepoItemEntity,
) {
    Card(
        shape = RectangleShape,
        colors = CardColors(
            containerColor = MaterialTheme.color.white,
            contentColor = MaterialTheme.color.black,
            disabledContainerColor = MaterialTheme.color.white,
            disabledContentColor = MaterialTheme.color.black
        )
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = repoItem.repoName, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.height(16.dp))
            Text(text = repoItem.repoFullName, style = MaterialTheme.typography.bodyMedium)
            Text(text = repoItem.repoDescription, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = modifier.height(16.dp))
            Row {
                Row(
                    modifier = modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Language,
                        contentDescription = ""
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(text = repoItem.language, style = MaterialTheme.typography.labelLarge)
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.StarBorder, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = "${repoItem.stargazersCount} Star",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Row(
                    modifier = modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.ForkRight, contentDescription = "")
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = "${repoItem.forksCount} Forked",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
