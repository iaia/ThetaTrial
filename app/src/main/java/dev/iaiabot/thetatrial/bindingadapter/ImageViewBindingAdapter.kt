package dev.iaiabot.thetatrial.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ImageViewBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.loadImage(url: String?) {
        url ?: return
        Picasso.get().load(url).into(this)
    }
}
