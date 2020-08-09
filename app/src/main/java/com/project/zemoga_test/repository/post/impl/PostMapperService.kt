package com.project.zemoga_test.repository.post.impl

import com.project.zemoga_test.services.api.post.model.PostApiModel
import com.project.zemoga_test.repository.post.IPostMapperService
import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.api.post.model.CommentApiModel
import com.project.zemoga_test.services.database.post.entities.PostEntity

class PostMapperService : IPostMapperService {

    override fun mapToPost(apiPost: PostApiModel): Post {
        return Post(
            id = apiPost.id,
            userId = apiPost.userId,
            title = apiPost.title,
            body = apiPost.body
        )
    }

    override fun mapToPost(postEntity: PostEntity): Post {
        return Post(
            id = postEntity.id,
            userId = postEntity.userId,
            title = postEntity.title,
            body = postEntity.body,
            isFavorite = postEntity.isFavorite,
            isRead = postEntity.isRead
        )
    }

    override fun mapToPostEntity(post: Post): PostEntity {
        return PostEntity(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body,
            isFavorite = post.isFavorite,
            isRead = post.isRead
        )
    }

    override fun mapToComment(apiComment: CommentApiModel): Comment {
        return Comment(apiComment.body)
    }
}