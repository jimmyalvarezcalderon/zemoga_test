package com.project.zemoga_test.repository.user.impl

import com.project.zemoga_test.repository.user.IUserMapperService
import com.project.zemoga_test.repository.user.IUserRepository
import com.project.zemoga_test.repository.user.model.User
import com.project.zemoga_test.services.api.RetrofitUtils
import com.project.zemoga_test.services.api.user.UserApiService
import io.reactivex.Flowable

class UserRepository(private val mapper: IUserMapperService) : IUserRepository {

    override fun getUser(userId: Int): Flowable<User> {
        return RetrofitUtils.getRetrofitClient(RetrofitUtils.BASE_URL).toFlowable()
            .flatMap { it.create(UserApiService::class.java).getAll() }
            .map { postList -> mapper.mapToUser(postList.first { it.id == userId }) }
    }
}
