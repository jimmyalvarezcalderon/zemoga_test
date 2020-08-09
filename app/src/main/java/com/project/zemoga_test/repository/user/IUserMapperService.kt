package com.project.zemoga_test.repository.user

import com.project.zemoga_test.repository.user.model.User
import com.project.zemoga_test.services.api.user.model.UserApiModel

interface IUserMapperService {
    fun mapToUser(userApiModel: UserApiModel): User
}