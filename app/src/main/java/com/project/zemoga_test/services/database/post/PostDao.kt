package com.project.zemoga_test.services.database.post

import androidx.room.*
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.database.post.entities.PostEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PostDao {

    @Query("SELECT * from post")
    fun getAll(): Flowable<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(postEntities: List<PostEntity>): Single<List<Long>>

    @Update
    fun updatePost(postEntity: PostEntity): Single<Int>

    @Query("DELETE from post")
    fun deleteAll(): Single<Int>

    @Delete
    fun deletePost(postEntity: PostEntity): Single<Int>
}