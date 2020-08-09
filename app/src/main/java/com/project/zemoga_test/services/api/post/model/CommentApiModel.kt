package com.project.zemoga_test.services.api.post.model

data class CommentApiModel(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)