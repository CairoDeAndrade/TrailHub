package com.restaurant.travel_counselor.features.login

import androidx.lifecycle.ViewModel
import com.restaurant.travel_counselor.dao.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val userDao: UserDao) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUserData())
    val uiState: StateFlow<LoginUserData> = _uiState

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    private fun validatePassword(): String {
        val password = _uiState.value.password
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters long"
            else -> ""
        }
    }

    private fun validateUsername(): String {
        val username = _uiState.value.username
        return when {
            username.isBlank() -> "Username is required"
            else -> ""
        }
    }

    fun login(): Boolean {
        val passwordError = validatePassword()
        val usernameError = validateUsername()

        return if (passwordError.isBlank() && usernameError.isBlank()) {
            true
        } else {
            _uiState.value =
                _uiState.value.copy(errorMessage = "$passwordError\n\n$usernameError")
            false
        }
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }

    suspend fun checkCredentials(): Boolean {
        val username = _uiState.value.username
        val password = _uiState.value.password
        val user = userDao.getUserByCredentials(username, password)
        return user != null
    }

}