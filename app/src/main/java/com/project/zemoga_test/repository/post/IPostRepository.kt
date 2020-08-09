package com.project.zemoga_test.repository.post

import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.post.model.Post
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IPostRepository {

    fun getAllFromNetwork(): Single<List<Post>>

    fun getAllFromDb(): Flowable<List<Post>>

    fun getCommentsByPost(postId: Int): Flowable<List<Comment>>

    fun syncPosts(): Single<List<Long>>

    fun addPostToFavorites(post: Post): Single<Int>

    fun markPostAsRead(post: Post): Single<Int>

    fun deleteAll(): Single<Int>

    fun delete(post: Post): Single<Int>
}
