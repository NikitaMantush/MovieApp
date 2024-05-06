package com.mantushnikita.movieapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val bestMovies = MutableLiveData<List<Doc>>()
    val newMovies = MutableLiveData<List<Doc>>()
    val upcomingMovies = MutableLiveData<List<Doc>>()

    fun loadBestMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getBestMovies()

            if (response.isSuccessful) {
                response.body()?.docs?.let { list ->
                    bestMovies.postValue(list)
                }
            }
        }
    }

    fun loadNewMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getNewtMovies()

            if (response.isSuccessful) {
                response.body()?.docs?.let { list ->
                    newMovies.postValue(list)
                }
            }
        }
    }

    fun loadUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = movieRepository.getUpcomingMovies()
            if (response.isSuccessful) {
                response.body()?.docs?.let { list ->
                    upcomingMovies.postValue(list)
                }
            }
        }
    }
}