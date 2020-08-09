package com.project.zemoga_test.services.api.post

import com.project.zemoga_test.services.api.post.model.CommentApiModel
import com.project.zemoga_test.services.api.post.model.PostApiModel
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {

    @GET("/posts")
    fun getAll(): Single<List<PostApiModel>>

    @GET("/posts/{postId}/comments")
    fun getCommentsByPost(@Path("postId") postId: Int): Flowable<List<CommentApiModel>>
}
