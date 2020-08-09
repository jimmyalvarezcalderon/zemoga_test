package com.project.zemoga_test.repository.user

import com.project.zemoga_test.repository.user.model.User
import io.reactivex.Flowable

interface IUserRepository {
    fun getUser(userId: Int): Flowable<User>
}