package com.project.zemoga_test.view.post

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.zemoga_test.view.post.adapter.PostListAdapter

class SwipeHandler(private val postListAdapter: PostListAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        postListAdapter.onPostSwipe(viewHolder.adapterPosition)
    }
}