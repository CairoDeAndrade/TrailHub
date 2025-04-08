package com.restaurant.travel_counselor.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.restaurant.travel_counselor.dao.UserDao

class LoginViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
