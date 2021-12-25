package com.example.devops.adapters

import com.example.devops.R
import android.content.Context

import android.widget.TextView

import com.smarteist.autoimageslider.SliderViewAdapter

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.devops.network.BASE_URL
import com.example.devops.screens.detailview.SliderItemDetailView

/**
 * Slider adapter
 */
class SliderAdapterDetailView(private val context: Context) :
    SliderViewAdapter<SliderAdapterDetailView.SliderAdapterVH>() {
    private var mSliderItems: MutableList<SliderItemDetailView> = ArrayList()

    /**
     * Renews products in the detail view
     */
    fun renewItems(sliderItems: MutableList<SliderItemDetailView>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderItemDetailView = mSliderItems[position]
        viewHolder.textViewDescription.visibility = View.GONE
        if (sliderItem.drawableId != null) {
                        viewHolder.imageViewBackground.setImageDrawable(context.getDrawable(sliderItem.drawableId!!))
                    } else {
                        val imageUrl = BASE_URL + sliderItem.imageUrl
                        val imgUri = imageUrl.toUri().buildUpon()?.scheme("http")?.build()
                        Glide.with(context)
                            .load(imgUri)
                            .dontAnimate()
                            .into(viewHolder.imageViewBackground)
                    }
    }

    override fun getCount(): Int {
        // slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {

        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        private var imageGifContainer: ImageView = itemView.findViewById(R.id.iv_gif_container)
        var textViewDescription: TextView

        init {
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }
}
