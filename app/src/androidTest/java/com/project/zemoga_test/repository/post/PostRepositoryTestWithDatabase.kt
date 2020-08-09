package com.project.zemoga_test.repository.post

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.zemoga_test.repository.post.impl.PostMapperService
import com.project.zemoga_test.repository.post.impl.PostRepository
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.database.ZemogaTestDataBase
import com.project.zemoga_test.services.database.post.entities.PostEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostRepositoryTestWithDatabase {

    private lateinit var db: ZemogaTestDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ZemogaTestDataBase::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `get_all_post_from_database`() {
//        Given
        val entities = listOf(PostEntity(1, 2, "", "", false, false))
        db.postDao().insertAll(entities).subscribe()
        val repository = PostRepository(PostMapperService(), db)
//        When
        val testSubscriber = repository.getAllFromDb().test().awaitCount(1)
//        Then
        testSubscriber.assertValue(listOf(Post(1, 2, "", "", false, false)))
    }

    @Test
    fun add_post_to_favorites() {
//        Given
        val postEntity = PostEntity(1, 1, "", "", true, false)
        db.postDao().insertAll(listOf(postEntity)).subscribe()
        val repository = PostRepository(PostMapperService(), db)
        val post = Post(1, 1, "", "", true, false)
//        When
        repository.addPostToFavorites(post).subscribe()
//        Then
        repository.getAllFromDb().test().awaitCount(1)
            .assertValueCount(1).assertValues(listOf(post))
    }

    @Test
    fun mark_post_to_read() {
        //        Given
        val postEntity = PostEntity(1, 1, "", "", true, true)
        db.postDao().insertAll(listOf(postEntity)).subscribe()
        val repository = PostRepository(PostMapperService(), db)
        val post = Post(1, 1, "", "", true, false)
//        When
        repository.markPostAsRead(post).subscribe()
//        Then
        repository.getAllFromDb().test().awaitCount(1)
            .assertValueCount(1).assertValues(listOf(post))
    }

    @Test
    fun delete_post() {
//        Given
        val postEntity = PostEntity(1, 1, "", "", true, true)
        db.postDao().insertAll(listOf(postEntity, postEntity.copy(id = 2))).subscribe()
        val repository = PostRepository(PostMapperService(), db)
        val post = Post(1, 1, "", "", true, false)
//        When
        repository.delete(post).subscribe()
//        Then
        repository.getAllFromDb().test().awaitCount(1).assertValueCount(1)
    }
}