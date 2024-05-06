package com.mantushnikita.movieapp.ui.movie.adapter.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.movieapp.databinding.ItemGenreBinding
import com.mantushnikita.movieapp.model.Genre

class GenreAdapter :
    ListAdapter<Genre, GenreViewHolder>(object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return false
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {

        return GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}