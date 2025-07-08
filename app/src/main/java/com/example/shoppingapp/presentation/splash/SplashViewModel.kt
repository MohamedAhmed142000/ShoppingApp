package com.example.shoppingapp.presentation.splash


import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.remote.firebase.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    suspend fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }
}
