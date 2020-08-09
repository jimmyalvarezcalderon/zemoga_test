package com.project.zemoga_test.view.post.model

import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.user.model.User

data class PostDetail(val description: String, val comments: List<Comment>, val user: User)