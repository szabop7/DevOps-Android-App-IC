package com.example.devops.screens.bindingutils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.devops.network.BASE_URL

fun setImageUrlAsImageView(context: Context, imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("http").build()
        Log.i("image", imgUri.toString())
        Glide.with(context)
            .load(imgUri)
            .dontAnimate()
            .into(imageView)
    }
}

// Adapter for imageURI
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    if (imgUrl != null) {
        setImageUrlAsImageView(context, this, BASE_URL + imgUrl)
    }
}
