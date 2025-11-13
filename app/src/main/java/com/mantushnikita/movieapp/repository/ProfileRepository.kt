package com.mantushnikita.movieapp.repository

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mantushnikita.movieapp.model.FavoriteMovie
import com.mantushnikita.movieapp.model.Profile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProfileRepository @Inject constructor() {

    fun loadUserInfo() = callbackFlow<Profile> {
        FirebaseAuth.getInstance().currentUser?.let {
            val profileReference =
                FirebaseDatabase.getInstance().getReference().child("Users").child(it.uid)
            val eventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.child("username").value.toString()
                    val email = snapshot.child("email").value.toString()
                    val profileImage = snapshot.child("profileImage").value.toString()
                    trySend(Profile(username, email, profileImage))
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            profileReference.addListenerForSingleValueEvent(eventListener)

            awaitClose {
                profileReference.removeEventListener(eventListener)
            }
        }
    }
}