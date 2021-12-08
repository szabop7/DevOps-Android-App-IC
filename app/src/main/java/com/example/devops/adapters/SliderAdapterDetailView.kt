package com.example.devops.adapters

import com.example.devops.R
import android.content.Context
import android.graphics.Color

import android.widget.TextView

import com.smarteist.autoimageslider.SliderViewAdapter

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.devops.network.BASE_URL
import com.example.devops.screens.bindingutils.setImage
import com.example.devops.screens.detailview.SliderItemDetailView

class SliderAdapterDetailView(context: Context) :
    SliderViewAdapter<SliderAdapterDetailView.SliderAdapterVH>() {
    private val context: Context
    private var mSliderItems: MutableList<SliderItemDetailView> = ArrayList()

    fun renewItems(sliderItems: MutableList<SliderItemDetailView>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderItemDetailView) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderItemDetailView = mSliderItems[position]
        viewHolder.textViewDescription.setText(sliderItem.description)
        viewHolder.textViewDescription.textSize = 16f
        viewHolder.textViewDescription.setTextColor(Color.WHITE)
        val imageUrl = BASE_URL + sliderItem.imageUrl
        val imgUri = imageUrl.toUri().buildUpon()?.scheme("http")?.build()
        Glide.with(context)
            .load(imgUri)
            .dontAnimate()
            .into(viewHolder.imageViewBackground)
    }

    override fun getCount(): Int {
        // slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {

        var imageViewBackground: ImageView
        var imageGifContainer: ImageView
        var textViewDescription: TextView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container)
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }

    init {
        this.context = context
    }
}
