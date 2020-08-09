package com.project.zemoga_test.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.zemoga_test.R
import com.project.zemoga_test.databinding.PostItemLayoutBinding
import com.project.zemoga_test.repository.post.model.Post

class PostListAdapter(val postEventHandler: PostEventHandler) :
    RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    private val items: MutableList<Post> = mutableListOf()

    fun newData(newItems: List<Post>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: PostItemLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.post_item_layout, parent, false)
        return PostViewHolder(view)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun onPostSwipe(adapterPosition: Int) {
        postEventHandler.onPostSwipe(items[adapterPosition])
    }

    inner class PostViewHolder(val view: PostItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(post: Post) {
            view.post = post
            view.root.setOnClickListener { postEventHandler.onPostClick(post) }
        }
    }

    interface PostEventHandler {
        fun onPostClick(post: Post)
        fun onPostSwipe(post: Post)
    }
}