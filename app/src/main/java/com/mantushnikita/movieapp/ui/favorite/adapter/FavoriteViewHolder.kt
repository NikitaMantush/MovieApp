package com.mantushnikita.movieapp.ui.favorite.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.databinding.ItemSearchBinding
import com.mantushnikita.movieapp.model.FavoriteMovie

class FavoriteViewHolder (
    private val binding: ItemSearchBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            movie: FavoriteMovie,
            onClick: (id:Int) -> Unit){

            binding.titleSearch.text = movie.name

            binding.filmImageSearch.run {
                Glide.with(context)
                    .load(movie.poster)
                    .into(this)
            }
            binding.rating.text = movie.rating?.toString()
            binding.yearLabel.text = movie.year.toString()
            binding.root.setOnClickListener {
                onClick(movie.id)
            }
        }
    }