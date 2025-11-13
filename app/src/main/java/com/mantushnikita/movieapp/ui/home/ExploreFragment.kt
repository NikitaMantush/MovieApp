package com.mantushnikita.movieapp.ui.home

import MovieListAdapter
import SearchListAdapter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.FragmentExploreBinding
import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.ui.home.adapter.MovieListViewHolder
import com.mantushnikita.movieapp.ui.home.adapter.SliderAdapter
import com.mantushnikita.movieapp.ui.home.adapter.SliderItems
import com.mantushnikita.movieapp.ui.movie.MovieFragment
import com.mantushnikita.movieapp.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private val slideHandler = Handler()

    private var binding: FragmentExploreBinding? = null

    private val viewModel: ExploreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postToList(binding?.sliderViewPager2)
        binding?.searchMovies?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment()).commit()
        }
        viewModel.bestMovies.observe(viewLifecycleOwner) {
            setList(it, binding?.recyclerViewBestMovie)
            binding?.progressBarBestMovies?.visibility = View.GONE
        }
        viewModel.newMovies.observe(viewLifecycleOwner) {
            setList(it, binding?.recyclerViewCategory)
            binding?.progressBarNewRelease?.visibility = View.GONE

        }
        viewModel.upcomingMovies.observe(viewLifecycleOwner) {
            setList(it, binding?.recyclerViewUpcoming)
            binding?.progressBarUpcoming?.visibility = View.GONE
        }
        viewModel.loadBestMovies()
        viewModel.loadNewMovies()
        viewModel.loadUpcomingMovies()
    }

    private fun setList(list: List<Doc>, recyclerView: RecyclerView?) {
        recyclerView?.run {
            if (adapter == null) {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter = MovieListAdapter { heroId ->
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MovieFragment().apply {
                            arguments = bundleOf("id" to heroId)
                        })
                        .addToBackStack(null)
                        .commit()
                }
            }
            (adapter as? MovieListAdapter)?.submitList(list)
        }
    }


    private fun postToList(viewpager: ViewPager2?) {
        val list = arrayListOf<SliderItems>()
        list.add(SliderItems(R.drawable.slide1))
        list.add(SliderItems(R.drawable.slide2))
        list.add(SliderItems(R.drawable.slide3))
        list.add(SliderItems(R.drawable.slide4))
        list.add(SliderItems(R.drawable.slide5))
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }
        viewpager?.apply {
            adapter = SliderAdapter(list, viewpager, requireContext())
            clipToPadding = false
            offscreenPageLimit = 3
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(compositePageTransformer)
            currentItem = 2
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    slideHandler.removeCallbacks(runnable)
                }
            })

        }
    }

    private val runnable = Runnable {
        val currentItem = binding?.sliderViewPager2?.currentItem ?: 0
        binding?.sliderViewPager2?.currentItem = currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        slideHandler.removeCallbacks(runnable)
    }
}