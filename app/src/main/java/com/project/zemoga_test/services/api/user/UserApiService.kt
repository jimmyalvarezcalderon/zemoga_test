package com.project.zemoga_test.services.api.user

import com.project.zemoga_test.services.api.user.model.UserApiModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface UserApiService {
    @GET("/users")
    fun getAll(): Flowable<List<UserApiModel>>
}
