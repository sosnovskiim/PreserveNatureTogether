package com.example.preservenaturetogether.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.preservenaturetogether.data.User
import com.example.preservenaturetogether.repository.UserRepository

class UserSignUpViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getUser(userLogin: String): User? =
        userRepository.getUserByLogin(userLogin = userLogin)

    fun addUser(userLogin: String, userPassword: String) {
        userRepository.addUser(userLogin = userLogin, userPassword = userPassword)
    }
}

class UserSignUpViewModelFactory(
    private val userRepository: UserRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UserSignUpViewModel(
            userRepository = userRepository,
        ) as T
}