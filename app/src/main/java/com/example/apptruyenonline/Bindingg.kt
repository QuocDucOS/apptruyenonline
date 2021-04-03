package com.example.apptruyenonline

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object Bindingg {
    @JvmStatic
    @BindingAdapter("loadImg")
    fun loadImg(img: ImageView,link:String) {
        Glide.with(img)
            .load(link)
            .error(R.drawable.khac)
            .into(img)
    }
    @BindingAdapter("loadImg2")
    fun loadImg2(img: ImageView,link:String) {
        Glide.with(img)
            .load(link)
            .error(R.drawable.anh7)
            .into(img)
    }
}