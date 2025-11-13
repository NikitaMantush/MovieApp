package com.mantushnikita.movieapp.ui.movie.adapter.genre

import androidx.recyclerview.widget.RecyclerView
import com.mantushnikita.movieapp.databinding.ItemGenreBinding
import com.mantushnikita.movieapp.model.Genre

class GenreViewHolder(
    private val binding: ItemGenreBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        genre: Genre
    ) {
        binding.genre.apply {
            text = genre.name
        }
    }
}