package com.mantushnikita.movieapp.ui.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.databinding.ItemSearchBinding
import com.mantushnikita.movieapp.model.Doc

class SearchListViewHolder(
    private val binding: ItemSearchBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Doc,
        onClick: (id:Int) -> Unit){

        binding.titleSearch.text = movie.name

        binding.filmImageSearch.run {
            Glide.with(context)
                .load(movie.poster?.previewUrl)
                .into(this)
        }
        binding.rating.text = movie.rating?.imdb.toString()
        binding.yearLabel.text = movie.year.toString()
        binding.root.setOnClickListener {
            onClick(movie.id)
        }
    }
}