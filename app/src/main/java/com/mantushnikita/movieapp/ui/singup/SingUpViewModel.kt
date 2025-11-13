package com.mantushnikita.movieapp.ui.singup

import androidx.lifecycle.ViewModel
import com.mantushnikita.movieapp.repository.SingUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val repository: SingUpRepository
) : ViewModel() {
    fun singUp(email: String, password: String, username: String,
               onSuccess: () -> Unit,
               onError: (Exception) -> Unit) {
        repository.singUp(email, password, username, onSuccess, onError)
    }
}