package com.mantushnikita.movieapp.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.movieapp.databinding.ItemSearchBinding
import com.mantushnikita.movieapp.model.FavoriteMovie
import com.mantushnikita.movieapp.ui.search.adapter.SearchListViewHolder

class FavoriteListAdapter (
    private val onClick: (id: Int) -> Unit
    ) : ListAdapter<FavoriteMovie, FavoriteViewHolder>(
    object : DiffUtil.ItemCallback<FavoriteMovie>() {
        override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return false
        }

    })
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
            return FavoriteViewHolder(
                ItemSearchBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
            holder.bind(getItem(position), onClick)
        }
    }