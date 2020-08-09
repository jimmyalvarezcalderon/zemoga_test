package com.project.zemoga_test.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.zemoga_test.R
import com.project.zemoga_test.databinding.CommentItemLayoutBinding
import com.project.zemoga_test.repository.post.model.Comment

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentHolder>() {

    private val items: MutableList<Comment> = mutableListOf()

    fun newData(newItems: List<Comment>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: CommentItemLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.comment_item_layout, parent, false)
        return CommentHolder(view)
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CommentHolder(val view: CommentItemLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(comment: Comment) {
            view.comment = comment
        }
    }
}
