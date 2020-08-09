package com.project.zemoga_test.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.project.zemoga_test.repository.post.impl.PostRepository
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.repository.user.impl.UserRepository
import com.project.zemoga_test.util.rxschduler.TrampolineSchedulerProvider
import com.project.zemoga_test.view.post.PostListViewModel
import com.project.zemoga_test.view.post.PostType
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class PostListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun get_all_post() {
//        Given
        val favoritePost = Post(1, 2, "", "", true, false)
        val post = Post(1, 2, "", "", false, false)
        val postRepository: PostRepository = mockk()
        val userRepository: UserRepository = mockk()
        val viewModel =
            PostListViewModel(
                postRepository,
                userRepository,
                TrampolineSchedulerProvider()
            )
        every { postRepository.getAllFromDb() } returns Flowable.just(listOf(post, favoritePost))
//        When
        viewModel.loadPost()
//        Then
        Assert.assertEquals(
            "Favorite posts are incorrect",
            listOf(post, favoritePost),
            viewModel.getPosts(PostType.ALL).getOrAwaitValue()
        )
    }

    @Test
    fun get_favorite_post() {
//        Given
        val favoritePost = Post(1, 2, "", "", true, false)
        val post = Post(1, 2, "", "", false, false)
        val postRepository: PostRepository = mockk()
        val userRepository: UserRepository = mockk()
        val viewModel =
            PostListViewModel(
                postRepository,
                userRepository,
                TrampolineSchedulerProvider()
            )
        every { postRepository.getAllFromDb() } returns Flowable.just(listOf(post, favoritePost))
//        When
        viewModel.loadPost()
//        Then
        Assert.assertEquals(
            "Favorite posts are incorrect",
            listOf(favoritePost),
            viewModel.getPosts(PostType.FAVORITES).getOrAwaitValue()
        )
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}