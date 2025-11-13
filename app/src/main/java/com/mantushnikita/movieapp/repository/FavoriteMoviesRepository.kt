package com.mantushnikita.movieapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.model.FavoriteMovie
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FavoriteMoviesRepository @Inject constructor() {

    fun checkIfMovieIsFavorite(
        movieId: Int,
        onSuccess: (Boolean) -> Unit,
        onError: (DatabaseError) -> Unit
    ) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            val databaseReference = FirebaseDatabase.getInstance().getReference()
            val favoriteMoviesReference =
                databaseReference.child("Users").child(user.uid).child("Favorite")
                    .child(movieId.toString())
            favoriteMoviesReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onSuccess.invoke(snapshot.exists())
                }

                override fun onCancelled(error: DatabaseError) {
                    onError.invoke(error)
                }
            })
        }
    }

    fun setFavoriteMovie(movie: Doc) {
        val movieInfo: HashMap<String, String> = HashMap()
        movieInfo["name"] = movie.name.toString()
        movieInfo["image"] = movie.poster?.previewUrl.toString()
        movieInfo["rating"] = movie.rating?.imdb.toString()
        movieInfo["year"] = movie.year.toString()
        FirebaseAuth.getInstance().currentUser?.let {
            FirebaseDatabase.getInstance().getReference().child("Users").child(it.uid)
                .child("Favorite").child(movie.id.toString())
                .setValue(movieInfo)
        }
    }

    fun removeMovieFromFavorites(movieId: Int) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            val databaseReference = FirebaseDatabase.getInstance().getReference()
            val favoriteMoviesReference =
                databaseReference.child("Users").child(user.uid).child("Favorite")
                    .child(movieId.toString())
            favoriteMoviesReference.removeValue()
        }
    }

    fun loadFavoriteMovies() = callbackFlow<List<FavoriteMovie>> {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            val databaseReference = FirebaseDatabase.getInstance().getReference()
            val favoriteMoviesReference =
                databaseReference.child("Users").child(user.uid).child("Favorite")
            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val favoriteMovies = ArrayList<FavoriteMovie>()
                    for (movieSnapshot in snapshot.children) {
                        val movieData = movieSnapshot.value as HashMap<String, String>
                        val id = movieSnapshot.key?.toIntOrNull() ?: continue
                        val name = movieData["name"]
                        val image = movieData["image"]
                        val rating = movieData["rating"]?.toDoubleOrNull()
                        val year = movieData["year"]

                        val doc = FavoriteMovie(
                            id = id,
                            name = name,
                            poster = image,
                            rating = rating,
                            year = year
                        )
                        favoriteMovies.add(doc)
                    }
                    trySend(favoriteMovies)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            favoriteMoviesReference.addListenerForSingleValueEvent(eventListener)

            awaitClose {
                favoriteMoviesReference.removeEventListener(eventListener)
            }
        }
    }
}
