package com.lerie_valerie.newsfeed.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lerie_valerie.newsfeed.databinding.FragmentNewsDetailBinding

class NewsDetailFragment: Fragment()  {
    private lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsDetailBinding.inflate(inflater, container, false)
        .apply { binding = this }.root
}