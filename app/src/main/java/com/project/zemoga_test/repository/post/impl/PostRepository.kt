package com.project.zemoga_test.repository.post.impl

import com.project.zemoga_test.repository.post.IPostMapperService
import com.project.zemoga_test.repository.post.IPostRepository
import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.api.RetrofitUtils
import com.project.zemoga_test.services.api.post.PostApiService
import com.project.zemoga_test.services.database.ZemogaTestDataBase
import io.reactivex.Flowable
import io.reactivex.Single

class PostRepository(
    private val mapper: IPostMapperService,
    private val database: ZemogaTestDataBase
) :
    IPostRepository {
    override fun getAllFromNetwork(): Single<List<Post>> {
        return RetrofitUtils.getRetrofitClient(RetrofitUtils.BASE_URL)
            .flatMap { it.create(PostApiService::class.java).getAll() }
            .map { postList -> postList.map { mapper.mapToPost(it) } }
            .map { markAsRead(it, 20) }
    }

    override fun getAllFromDb(): Flowable<List<Post>> {
        return database.postDao().getAll().map { entities -> entities.map { mapper.mapToPost(it) } }
    }

    override fun getCommentsByPost(postId: Int): Flowable<List<Comment>> {
        return RetrofitUtils.getRetrofitClient(RetrofitUtils.BASE_URL).toFlowable()
            .flatMap { it.create(PostApiService::class.java).getCommentsByPost(postId) }
            .map { postList -> postList.map { mapper.mapToComment(it) } }
    }

    override fun syncPosts(): Single<List<Long>> {
        return getAllFromNetwork()
            .map { postList -> postList.map { mapper.mapToPostEntity(it) } }
            .flatMap { database.postDao().insertAll(it) }
    }

    override fun addPostToFavorites(post: Post): Single<Int> {
        return database.postDao().updatePost(mapper.mapToPostEntity(post))
    }

    override fun markPostAsRead(post: Post): Single<Int> {
        return database.postDao().updatePost(mapper.mapToPostEntity(post))
    }

    override fun deleteAll(): Single<Int> {
        return database.postDao().deleteAll()
    }

    override fun delete(post: Post): Single<Int> {
        return database.postDao().deletePost(mapper.mapToPostEntity(post))
    }

    private fun markAsRead(it: List<Post>, amount: Int) =
        it.take(amount).map { it.copy(isRead = false) }
            .plus(it.drop(amount).map { it.copy(isRead = true) })

}