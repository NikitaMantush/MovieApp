package com.mantushnikita.movieapp.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentFavoriteBinding
import com.mantushnikita.movieapp.model.FavoriteMovie
import com.mantushnikita.movieapp.ui.favorite.adapter.FavoriteListAdapter
import com.mantushnikita.movieapp.ui.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var binding: FragmentFavoriteBinding? = null

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadFavoriteMovies()
                viewModel.favoriteMovies.collectLatest { favoriteMovies ->
                    setList(favoriteMovies, binding?.recyclerViewFavorite)
                }
            }
        }
    }

    private fun setList(list: List<FavoriteMovie>, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                adapter = FavoriteListAdapter { heroId ->
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MovieFragment().apply {
                            arguments = bundleOf("id" to heroId)
                        })
                        .addToBackStack(null)
                        .commit()
                }
            }
            (adapter as? FavoriteListAdapter)?.submitList(list)
        }
    }
}