package com.project.zemoga_test.repository.post

import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.services.api.post.model.PostApiModel
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.api.post.model.CommentApiModel
import com.project.zemoga_test.services.database.post.entities.PostEntity

interface IPostMapperService {
    fun mapToPost(apiPost: PostApiModel): Post
    fun mapToPost(postEntity: PostEntity): Post
    fun mapToPostEntity(post: Post): PostEntity
    fun mapToComment(apiComment: CommentApiModel): Comment
}