package com.project.zemoga_test.view.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.project.zemoga_test.repository.post.IPostRepository
import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.repository.user.IUserRepository
import com.project.zemoga_test.repository.user.model.User
import com.project.zemoga_test.util.rxschduler.BaseSchedulerProvider
import com.project.zemoga_test.view.post.model.PostDetail
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class PostListViewModel(
    private val postRepository: IPostRepository,
    private val userRepository: IUserRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    private val _postDetail: MutableLiveData<PostDetail> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun getPosts(postType: PostType): LiveData<List<Post>> = Transformations.map(_posts) {
        if (postType == PostType.FAVORITES) it.filter { it.isFavorite } else it
    }

    fun getPostDetail(): LiveData<PostDetail> = _postDetail

    fun syncPost() {
        postRepository.syncPosts()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { Log.e(TAG, "Post synced, IDs: $it") },
                { Log.e(TAG, "Error syncing posts: $it") }
            ).also { disposables.add(it) }
    }

    fun loadPostDetail(post: Post) {
        Flowable.zip(
            postRepository.getCommentsByPost(post.id),
            userRepository.getUser(post.userId),
            BiFunction { comments: List<Comment>, user: User ->
                return@BiFunction PostDetail(post.body, comments, user)
            })
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { _postDetail.postValue(it) },
                { Log.e(TAG, "Error loading post detail") })
            .also { disposables.add(it) }
    }

    fun loadPost() {
        postRepository.getAllFromDb()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { _posts.postValue(it) },
                { Log.e(TAG, "Error syncing posts: $it") }
            ).also { disposables.add(it) }
    }

    fun addToFavorite(post: Post, isFavorite: Boolean) {
        postRepository.addPostToFavorites(post.copy(isFavorite = isFavorite))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    Log.d(TAG, "Post marked as Favorite!")
                },
                {
                    Log.e(TAG, "Error marking post as favorite. Error: $it")
                }).also { disposables.add(it) }
    }

    fun markAsRead(post: Post) {
        postRepository.markPostAsRead(post.copy(isRead = true))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    Log.d(TAG, "Post marked as read!")
                },
                {
                    Log.e(TAG, "Error marking post as read. Error: $it")
                }).also { disposables.add(it) }
    }

    fun deletePosts() {
        postRepository.deleteAll()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    Log.d(TAG, "Posts Deleted")
                },
                {
                    Log.e(TAG, "Error deleting posts. Error: $it")
                }).also { disposables.add(it) }
    }

    fun deletePost(post: Post) {
        postRepository.delete(post)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                {
                    Log.d(TAG, "Post Deleted")
                },
                {
                    Log.e(TAG, "Error deleting post. Error: $it")
                }).also { disposables.add(it) }
    }

    companion object {
        const val TAG = "PostListViewModel"
    }
}