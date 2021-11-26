package com.alltrails.lunch.ui.nearbyrestaurants

import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("priceLevel")
fun priceLevel(textView: TextView, priceLevel: Int?) {
    if (priceLevel != null) {
        val context = textView.context
        val text = when (priceLevel) {
            0 -> context.getString(R.string.price_level_free)
            1 -> context.getString(R.string.price_level_inexpensive)
            2 -> context.getString(R.string.price_level_moderate)
            3 -> context.getString(R.string.price_level_expensive)
            4 -> context.getString(R.string.price_level_very_expensive)
            else -> context.getString(R.string.price_level_unknown)
        }
        textView.text = text
    }
}