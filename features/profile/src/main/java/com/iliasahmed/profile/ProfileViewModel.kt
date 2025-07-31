package com.iliasahmed.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliasahmed.domain.usecase.ProfileUseCase
import com.iliasahmed.domain.usecase.RepoListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.ProfileEntity
import com.iliasahmed.entity.RepoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private var repoListUseCase: RepoListUseCase
) : ViewModel() {
    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState get() = _profileUiState.asStateFlow()

    private lateinit var username: String

    fun setUserName(userName: String) {
        username = userName
        fetchProfileAndRepos()
    }

    private fun fetchProfileAndRepos() {
        viewModelScope.launch {
            _profileUiState.value = ProfileUiState.Loading

            combine(
                profileUseCase.execute(ProfileUseCase.Params(userName = username)),
                repoListUseCase.execute(RepoListUseCase.Params(userName = username))
            ) { profileResult, repoResult ->

                when {
                    profileResult is Result.Success && repoResult is Result.Success -> {
                        ProfileUiState.Success(
                            profile = profileResult.data,
                            repositories = repoResult.data,
                        )
                    }

                    profileResult is Result.Error -> {
                        ProfileUiState.Error(profileResult.message)
                    }

                    repoResult is Result.Error -> {
                        ProfileUiState.Error(repoResult.message)
                    }

                    profileResult is Result.Loading || repoResult is Result.Loading -> {
                        ProfileUiState.Loading
                    }

                    else -> {
                        ProfileUiState.Loading
                    }
                }

            }.collect { combinedState ->
                _profileUiState.value = combinedState
            }
        }
    }


    fun handleAction(action: ProfileUiAction) {
        when (action) {
            ProfileUiAction.FetchProfile -> fetchProfileAndRepos()
        }
    }
}

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val profile: ProfileEntity,
        val repositories: List<RepoItemEntity>
    ) : ProfileUiState

    data class Error(val message: String) : ProfileUiState
}

sealed interface ProfileUiAction {
    data object FetchProfile : ProfileUiAction
}