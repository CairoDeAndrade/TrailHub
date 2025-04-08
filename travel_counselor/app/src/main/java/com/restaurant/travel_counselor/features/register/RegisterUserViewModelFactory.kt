package com.restaurant.travel_counselor.features.register

import androidx.lifecycle.ViewModel
import com.restaurant.travel_counselor.dao.UserDao

class RegisterUserViewModelFactory(
    private val userDao: UserDao
): androidx.lifecycle.ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterUserViewModel(userDao) as T
    }
}