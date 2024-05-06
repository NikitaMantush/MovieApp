package com.mantushnikita.movieapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseError
import com.mantushnikita.movieapp.model.FavoriteMovie
import com.mantushnikita.movieapp.repository.FavoriteMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: FavoriteMoviesRepository
) : ViewModel() {

    val favoriteMovies = MutableStateFlow<List<FavoriteMovie>>(arrayListOf())

    fun loadFavoriteMovies() {
        viewModelScope.launch {
            repository.loadFavoriteMovies().collect { movie ->
                favoriteMovies.value = movie
            }
        }
    }
}
