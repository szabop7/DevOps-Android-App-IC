package com.example.devops.network

import com.example.devops.database.devops.product.Product

data class ApiProduct(

    val body: List<Product>
) {
    // Hardcoded image url to demo Glide
    val smileyUri = "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/285/grinning-face-with-big-eyes_1f603.png"
}