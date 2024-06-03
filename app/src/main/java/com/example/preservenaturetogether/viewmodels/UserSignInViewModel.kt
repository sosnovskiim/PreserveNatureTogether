package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.User
import com.example.preservenaturetogether.repository.UserRepository

class UserSignInViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getUser(userLogin: String, userPassword: String): User? =
        userRepository.getUserByLoginAndPassword(userLogin = userLogin, userPassword = userPassword)
}

class UserSignInViewModelFactory(
    private val userRepository: UserRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserSignInViewModel(
            userRepository = userRepository,
        ) as T
}