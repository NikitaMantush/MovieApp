package com.mantushnikita.movieapp.ui.movie.adapter.person

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.movieapp.databinding.ItemPersonBinding
import com.mantushnikita.movieapp.model.Person

class PersonAdapter :
    ListAdapter<Person, PersonViewHolder>(object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return false
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}