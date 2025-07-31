package com.iliasahmed.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iliasahmed.domain.usecase.UsersListUseCase
import com.iliasahmed.domain.utils.Result
import com.iliasahmed.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val usersListUseCase: UsersListUseCase
) : ViewModel() {
    private val _usersListUiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Loading)
    val usersListUiState get() = _usersListUiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun fetchUsers(newQuery: String = "") {
        _searchQuery.value = newQuery
        fetchUsersList()
    }

    private fun fetchUsersList() {
        _usersListUiState.value = UsersListUiState.Loading
        viewModelScope.launch {
            usersListUseCase.execute(UsersListUseCase.Params(searchQuery.value))
                .collect { response ->
                    when (response) {
                        is Result.Error -> _usersListUiState.value =
                            UsersListUiState.Error(response.message)

                        is Result.Loading -> _usersListUiState.value = UsersListUiState.Loading
                        is Result.Success -> {
                            if (response.data.isEmpty()) {
                                _usersListUiState.value = UsersListUiState.UsersListEmpty
                                return@collect
                            }
                            _usersListUiState.value = UsersListUiState.HasUsersList(response.data)
                        }
                    }
                }
        }
    }


    fun handleAction(action: UsersListUiAction) {
        when (action) {
            UsersListUiAction.FetchUsersList -> fetchUsersList()
        }
    }
}

sealed interface UsersListUiState {
    data object Loading : UsersListUiState
    data class HasUsersList(val usersList: List<UserEntity>) : UsersListUiState
    data object UsersListEmpty : UsersListUiState
    data class Error(val message: String) : UsersListUiState
}

sealed interface UsersListUiAction {
    data object FetchUsersList : UsersListUiAction
}