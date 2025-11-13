package com.mantushnikita.movieapp.ui.movie.adapter.person

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.databinding.ItemPersonBinding
import com.mantushnikita.movieapp.model.Person

class PersonViewHolder(
    private val binding:ItemPersonBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        person: Person
    ){
        binding.personName.text = person.name

        binding.personPhoto.run {
            Glide.with(context)
                .load(person.photo)
                .into(this)
        }
    }
}