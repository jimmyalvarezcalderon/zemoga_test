package com.project.zemoga_test.view.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.project.zemoga_test.R
import com.project.zemoga_test.databinding.FragmentPostDetailBinding
import com.project.zemoga_test.view.post.adapter.CommentsAdapter
import kotlinx.android.synthetic.main.fragment_post_detail.*
import kotlinx.android.synthetic.main.fragment_post_detail.toolbar
import kotlinx.android.synthetic.main.post_fragment.*
import org.koin.android.ext.android.inject

class PostDetailFragment : Fragment() {

    private val detailArgs: PostDetailFragmentArgs by navArgs()
    private val postListViewModel: PostListViewModel by inject()
    private val commentAdapter: CommentsAdapter = CommentsAdapter()
    private lateinit var postDetailBinding: FragmentPostDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postListViewModel.loadPostDetail(detailArgs.post)
        postDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)
        postDetailBinding.lifecycleOwner = this
        return postDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTopBar()
        setComments()
        postListViewModel.getPostDetail().observe(this.viewLifecycleOwner, Observer {
            postDetailBinding.postDetail = it
            commentAdapter.newData(it.comments)
        })
    }

    private fun setComments() {
        commentList.adapter = commentAdapter
    }

    private fun setUpTopBar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.setOnMenuItemClickListener(getMenuItemClickListener())
    }

    private fun getMenuItemClickListener(): (item: MenuItem) -> Boolean = {
        when (it.itemId) {
            R.id.action_add_favorite -> {
                it.isChecked = !detailArgs.post.isFavorite
                postListViewModel.addToFavorite(detailArgs.post, it.isChecked)
                true
            }
            else -> {
                Log.d("PostFragment", "Menu item not implemented")
                true
            }
        }
    }
}