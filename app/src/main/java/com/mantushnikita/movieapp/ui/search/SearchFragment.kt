package com.mantushnikita.movieapp.ui.search

import SearchListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentSearchingMoviesBinding
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.ui.MainFragment
import com.mantushnikita.movieapp.ui.home.ExploreFragment
import com.mantushnikita.movieapp.ui.movie.MovieFragment
import com.mantushnikita.movieapp.util.openFragment
import com.mantushnikita.movieapp.util.openFragmentWithBottomMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var binding: FragmentSearchingMoviesBinding? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchingMoviesBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            searchView.clearFocus()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }
            })
            toolbarBack.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commit()
            }
        }
    }

    fun filterList(text: String?) {
        text?.let { viewModel.searchMovies(it) }
        viewModel.movies.observe(viewLifecycleOwner) {
            setList(it, binding?.recyclerViewSearch)
        }
    }

    private fun setList(list: List<Doc>, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                adapter = SearchListAdapter { heroId ->
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MovieFragment().apply {
                            arguments = bundleOf("id" to heroId)
                        })
                        .addToBackStack(null)
                        .commit()
                }
            }
            (adapter as? SearchListAdapter)?.submitList(list)
        }
    }
}