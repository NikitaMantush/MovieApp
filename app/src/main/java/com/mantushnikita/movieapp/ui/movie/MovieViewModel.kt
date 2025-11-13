package com.mantushnikita.movieapp.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.repository.FavoriteMoviesRepository
import com.mantushnikita.movieapp.repository.MovieRepository
import com.mantushnikita.movieapp.util.toDoc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val favoriteMovieRepository: FavoriteMoviesRepository
) : ViewModel() {

    val movie = MutableLiveData<Doc>()

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovieById(id)

            if (response.isSuccessful) {
                movie.postValue(response.body()?.toDoc())
            }
        }
    }

    fun setFavoriteMovie(movie: Doc){
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieRepository.setFavoriteMovie(movie)
        }
    }

    fun removeMovieFromFavorites(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieRepository.removeMovieFromFavorites(movieId)
        }
    }

    fun checkIfMovieIsFavorite(
        movieId: Int,
        onSuccess: (Boolean) -> Unit,
        onError: (DatabaseError) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieRepository.checkIfMovieIsFavorite(movieId, onSuccess, onError)
        }
    }
}