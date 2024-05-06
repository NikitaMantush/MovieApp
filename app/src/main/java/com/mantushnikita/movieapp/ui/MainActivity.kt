package com.mantushnikita.movieapp.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentMainBinding
import com.mantushnikita.movieapp.repository.SharedPreferencesRepository
import com.mantushnikita.movieapp.ui.login.LoginFragment
import com.mantushnikita.movieapp.util.openFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        when {
//            sharedPreferencesRepository.isFirstLaunch() -> {
//                supportFragmentManager.openFragment(StartFragment())
//                sharedPreferencesRepository.setIsFirstLaunch()
//            }
            sharedPreferencesRepository.getUserEmail() == null -> {
                supportFragmentManager.openFragment(LoginFragment())
            }
            else -> {
                supportFragmentManager.openFragment(MainFragment())
            }
        }
    }
}