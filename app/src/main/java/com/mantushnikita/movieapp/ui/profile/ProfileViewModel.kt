package com.mantushnikita.movieapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.movieapp.model.Profile
import com.mantushnikita.movieapp.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): ViewModel() {

    val profile = MutableStateFlow<Profile>(Profile("","",""))

    fun loadUserInfo() {
        viewModelScope.launch {
            repository.loadUserInfo().collect { profileFlow ->
                profile.value = profileFlow
            }
        }
    }

}