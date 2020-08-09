package com.project.zemoga_test.view.post.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.zemoga_test.view.post.PostListFragment
import com.project.zemoga_test.view.post.PostType

class PostsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostListFragment(
                PostType.ALL
            )
            else -> PostListFragment(
                PostType.FAVORITES
            )
        }
    }
}