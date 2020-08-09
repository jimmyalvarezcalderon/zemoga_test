package com.project.zemoga_test.view.post

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.project.zemoga_test.R
import com.project.zemoga_test.view.post.adapter.PostsPagerAdapter
import kotlinx.android.synthetic.main.post_fragment.*
import org.koin.android.ext.android.inject

open class PostFragment constructor(): Fragment() {

    private lateinit var postPagerAdapter: PostsPagerAdapter
    private val viewModel: PostListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.syncPost()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTopBar()
        postPagerAdapter = PostsPagerAdapter(this)
        postPager.adapter = postPagerAdapter
        TabLayoutMediator(tabLayout, postPager) { tab, position ->
            when (position) {
                0 -> tab.text = "ALL"
                else -> tab.text = "FAVORITES"
            }
        }.attach()
        deleteFab.setOnClickListener { viewModel.deletePosts() }
    }

    private fun setUpTopBar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.setOnMenuItemClickListener(getMenuItemClickListener())
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPost()
    }

    private fun getMenuItemClickListener(): (item: MenuItem) -> Boolean = {
        when (it.itemId) {
            R.id.action_reload -> {
                viewModel.syncPost()
                true
            }
            else -> {
                Log.d("PostFragment", "Menu item not implemented")
                true
            }
        }
    }

}