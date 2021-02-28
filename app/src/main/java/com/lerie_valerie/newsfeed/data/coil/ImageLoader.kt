package com.lerie_valerie.newsfeed.data.coil

import android.content.Context
import coil.Coil

object ImageLoader {
    fun build(context: Context) =
        Coil.imageLoader(context)
}