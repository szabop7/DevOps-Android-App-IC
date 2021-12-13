package com.example.devops.screens.bindingutils

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.devops.network.BASE_URL

// Adapter for imageURI
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    if (imgUrl != null) {
        val full = BASE_URL + imgUrl
        full.let {
            val imgUri = it.toUri().buildUpon().scheme("http").build()
            Log.i("image", imgUri.toString())
            Glide.with(context)
                .load(imgUri)
                .dontAnimate()
                .into(this)
        }
    }
}
