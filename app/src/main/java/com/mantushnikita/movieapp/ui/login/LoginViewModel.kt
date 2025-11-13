package com.mantushnikita.movieapp.ui.login

import androidx.lifecycle.ViewModel
import com.mantushnikita.movieapp.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    fun login(email: String, password: String,
              onSuccess: () -> Unit,
              onError: (Exception) -> Unit) {
        repository.login(email, password, onSuccess, onError)
    }
}