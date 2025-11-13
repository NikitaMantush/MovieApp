package com.mantushnikita.movieapp.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.databinding.ItemMovieBinding
import com.mantushnikita.movieapp.model.Doc

class MovieListViewHolder(
    private val binding: ItemMovieBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Doc,
        onClick: (id:Int) -> Unit){

        binding.filmNameItem.text = movie.name

        binding.filmImageItem.run {
            Glide.with(context)
                .load(movie.poster?.previewUrl)
                .into(this)
        }
        binding.root.setOnClickListener {
            onClick(movie.id)
        }
    }
}