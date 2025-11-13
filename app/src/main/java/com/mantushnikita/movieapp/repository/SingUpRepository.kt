package com.mantushnikita.movieapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SingUpRepository @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    fun singUp(
        email: String, password: String, username: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            onSuccess.invoke()
            sharedPreferencesRepository.setUserEmail(email)
            val userInfo: HashMap<String, String> = HashMap()
            userInfo["email"] = email
            userInfo["username"] = username
            userInfo["profileImage"] = ""
            FirebaseAuth.getInstance().currentUser?.let {
                FirebaseDatabase.getInstance().getReference().child("Users").child(it.uid)
                    .setValue(userInfo)
            }
        }.addOnFailureListener { exception ->
            onError.invoke(exception)
        }
    }
}