package com.lerie_valerie.newsfeed.presentation.roster

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.lerie_valerie.newsfeed.databinding.FragmentNewsFeedRosterBinding

class NewsFeedRosterFragment: Fragment() {
    private lateinit var binding: FragmentNewsFeedRosterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsFeedRosterBinding.inflate(inflater, container, false)
        .apply { binding = this }.root

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.actions, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.load -> {
//                viewModel.reloadFromRemoteFailure()
//                viewModel.load()
//                return true
//            }
//            R.id.delete -> {
//                viewModel.delete()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}