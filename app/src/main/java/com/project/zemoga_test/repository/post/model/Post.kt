package com.project.zemoga_test.repository.post.model

import java.io.Serializable

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false
) : Serializable