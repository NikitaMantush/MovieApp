package com.mantushnikita.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentMovieBinding
import com.mantushnikita.movieapp.model.Genre
import com.mantushnikita.movieapp.model.Person
import com.mantushnikita.movieapp.ui.MainFragment
import com.mantushnikita.movieapp.ui.home.ExploreFragment
import com.mantushnikita.movieapp.ui.movie.adapter.genre.GenreAdapter
import com.mantushnikita.movieapp.ui.movie.adapter.person.PersonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var binding: FragmentMovieBinding? = null

    private var isFavoriteChecked: Boolean = false

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { movieId ->
            viewModel.getMovie(movieId)
        }
        viewModel.movie.observe(viewLifecycleOwner) { movie ->

            viewModel.checkIfMovieIsFavorite(movie.id,
                onSuccess = { isFavorite ->
                    if (isFavorite) {
                        binding?.favoriteButton?.setImageResource(R.drawable.ic_favorite)
                        isFavoriteChecked = true
                    } else {
                        binding?.favoriteButton?.setImageResource(R.drawable.ic_favorite_unfilled)
                        isFavoriteChecked = false
                    }
                }, onError = {

                })

            binding?.run {
                filmImage.run {
                    Glide.with(requireContext())
                        .load(movie.poster?.url)
                        .into(this)
                }
                filmNameTextView.text = movie.name
                rating.text = movie.rating?.imdb.toString()
                duration.text = movie.movieLength.toString()
                setGenreList(movie?.genres, binding?.genreRecyclerview)
                descriptionTextView.text = movie.description
                setPersonList(
                    movie?.persons?.filter { it.enProfession == "actor" },
                    binding?.actorsRecyclerview
                )
            }
            binding?.favoriteButton?.setOnClickListener {
                if (isFavoriteChecked) {
                    viewModel.removeMovieFromFavorites(movie.id)
                    binding?.favoriteButton?.setImageResource(R.drawable.ic_favorite_unfilled)
                }
                else{
                    viewModel.setFavoriteMovie(movie)
                    binding?.favoriteButton?.setImageResource(R.drawable.ic_favorite)
                }
            }
            binding?.backButton?.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment()).commit()
            }
        }
    }

    private fun setGenreList(list: List<Genre>?, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter = GenreAdapter()
            }
            (adapter as? GenreAdapter)?.submitList(list)
        }
    }

    private fun setPersonList(list: List<Person>?, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter = PersonAdapter()
            }
            (adapter as? PersonAdapter)?.submitList(list)
        }
    }

}