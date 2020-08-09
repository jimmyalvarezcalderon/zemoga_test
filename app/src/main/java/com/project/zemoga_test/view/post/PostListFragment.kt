package com.project.zemoga_test.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.project.zemoga_test.R
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.view.post.adapter.PostListAdapter
import kotlinx.android.synthetic.main.post_list_layout.*
import org.koin.android.ext.android.inject

class PostListFragment(private val postType: PostType) : Fragment(),
    PostListAdapter.PostEventHandler {

    private val postListViewModel: PostListViewModel by inject()
    private lateinit var listAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPostList()
        postListViewModel.getPosts(postType).observe(this.viewLifecycleOwner, Observer {
            listAdapter.newData(it)
        })

    }

    private fun setUpPostList() {
        listAdapter = PostListAdapter(this)
        postsList.adapter = listAdapter
        setUpSwipeEvent(listAdapter)
    }

    private fun setUpSwipeEvent(listAdapter: PostListAdapter) {
        val itemTouchHelper = ItemTouchHelper(SwipeHandler(listAdapter))
        itemTouchHelper.attachToRecyclerView(postsList)
    }

    override fun onPostClick(post: Post) {
        postListViewModel.markAsRead(post)
        val action = PostFragmentDirections.actionToPostDetail(post)
        findNavController().navigate(action)
    }

    override fun onPostSwipe(post: Post) {
        postListViewModel.deletePost(post)
    }
}