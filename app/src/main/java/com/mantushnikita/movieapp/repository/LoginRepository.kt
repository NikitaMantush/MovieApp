    package com.mantushnikita.movieapp.repository

    import com.google.firebase.auth.ktx.auth
    import com.google.firebase.ktx.Firebase
    import javax.inject.Inject

    class LoginRepository @Inject constructor(
       private var sharedPreferencesRepository: SharedPreferencesRepository
    ) {
        fun login( email: String,
                   password: String,
                   onSuccess: () -> Unit,
                   onError: (Exception) -> Unit) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    onSuccess.invoke()
                    sharedPreferencesRepository.setUserEmail(email)
                }
                .addOnFailureListener { exception ->
                    onError.invoke(exception)
                }
        }
    }