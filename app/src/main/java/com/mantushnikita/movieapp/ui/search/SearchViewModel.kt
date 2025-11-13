package com.mantushnikita.movieapp.ui.search

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
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel(){

    val movies = MutableLiveData<List<Doc>>()

    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchMovies(query)
            if (response.isSuccessful) {
                response.body()?.docs?.let { list ->
                    movies.postValue(list)
                }
            }
        }
    }

}