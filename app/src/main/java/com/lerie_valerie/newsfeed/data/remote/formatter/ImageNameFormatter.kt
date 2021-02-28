package com.lerie_valerie.newsfeed.data.remote.formatter

class ImageNameFormatter {
    companion object {
        fun getImageName(url: String?) =
            url?.let {
                it.substring(it.lastIndexOf('/') + 1, it.length)
            }
    }
}