package com.example.shoppingapp.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.remote.firebase.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun register(onSuccess: () -> Unit) {
        if (password != confirmPassword) {
            errorMessage = "كلمتا المرور غير متطابقتين"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = authRepository.register(email, password)
            isLoading = false

            result.onSuccess {
                onSuccess()
            }.onFailure {
                errorMessage = it.message
            }
        }
    }
}
