package com.lerie_valerie.newsfeed.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lerie_valerie.newsfeed.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}