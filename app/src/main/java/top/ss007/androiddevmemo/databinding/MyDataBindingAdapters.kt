package top.ss007.androiddevmemo.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Copyright (C) 2020 shusheng007
 * 完全享有此软件的著作权，违者必究
 *
 * @author       shusheng007
 * @modifier
 * @createDate   2020/5/23 23:15
 * @version      1.0
 * @description
 */
object MyDataBindingAdapters {
    @JvmStatic
    @BindingAdapter("wrapWithSymbol")
    fun wrapWithSymbol(view: TextView, symbol: String) {
        view.text = "$symbol${view.text}$symbol"
    }

    @JvmStatic
    @BindingAdapter(value=["imageUrl", "error"],requireAll = true)
    fun loadImage(view: ImageView, url: String, error: Drawable) {
//        Picasso.get().load(url).error(error).into(view)
    }
}