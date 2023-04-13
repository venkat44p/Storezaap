package com.example.storezaapdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SliderImageAdapter(private val context: Context, private val imageList: List<String>) :
    RecyclerView.Adapter<SliderImageAdapter.SliderImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderImageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.id.sliderLayout, parent, false)
        return SliderImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderImageViewHolder, position: Int) {
        // Load the image into the ImageView
        Picasso.get().load(imageList[position]).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class SliderImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageSlider)
    }
}
