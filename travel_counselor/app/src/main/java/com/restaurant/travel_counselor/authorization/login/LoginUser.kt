package com.restaurant.travel_counselor.authorization.login


data class LoginUser(
    val username: String = "",
    val password: String = "",
    val errorMessage: String = "",
) {
    fun validateFields(): String {
        return when {
            username.isBlank() -> "Username is required"
            password.isBlank() -> "Password is required"
            else -> ""
        }
    }

    fun validatePassword(): String {
        if (password.isBlank()) {
            return "Password is required"
        }
        return ""
    }
}