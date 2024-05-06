package com.mantushnikita.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentMainBinding
import com.mantushnikita.movieapp.ui.favorite.FavoriteFragment
import com.mantushnikita.movieapp.ui.home.ExploreFragment
import com.mantushnikita.movieapp.ui.profile.ProfileFragment
import com.mantushnikita.movieapp.util.openFragmentWithBottomMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager
            .openFragmentWithBottomMenu(ExploreFragment())
        binding?.bottomNavigationView?.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.home ->{
                    childFragmentManager
                        .openFragmentWithBottomMenu(ExploreFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.search ->{
                    childFragmentManager
                        .openFragmentWithBottomMenu(FavoriteFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.profile ->{
                    childFragmentManager
                        .openFragmentWithBottomMenu(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}