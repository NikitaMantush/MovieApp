package com.mantushnikita.movieapp.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.mantushnikita.movieapp.R
import com.mantushnikita.movieapp.databinding.ItemSliderBinding

class SliderAdapter(
    private val sliderItems: ArrayList<SliderItems>,
    private val viewPager2: ViewPager2?,
    private val context: Context
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(val binding: ItemSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun setImage(sliderItems:SliderItems){
                Glide.with(context)
                    .load(sliderItems.image).into(binding.imageSlide)
            }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        val itemBinding =
            ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size-1){
            viewPager2?.post(runnable)
        }
    }
    override fun getItemCount(): Int {
        return sliderItems.size
    }
    private val runnable = Runnable {
        sliderItems.addAll(0, sliderItems)
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

}