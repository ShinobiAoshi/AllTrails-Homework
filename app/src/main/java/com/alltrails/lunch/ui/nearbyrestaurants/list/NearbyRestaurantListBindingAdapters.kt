package com.alltrails.lunch.ui.nearbyrestaurants.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.alltrails.lunch.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_image_placeholder)
            error(R.drawable.ic_image_error)
        }
    }
}