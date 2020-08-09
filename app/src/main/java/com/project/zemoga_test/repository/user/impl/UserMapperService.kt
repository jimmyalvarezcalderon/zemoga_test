package com.project.zemoga_test.repository.user.impl

import com.project.zemoga_test.repository.user.IUserMapperService
import com.project.zemoga_test.repository.user.model.User
import com.project.zemoga_test.services.api.user.model.UserApiModel

class UserMapperService : IUserMapperService {

    override fun mapToUser(userApiModel: UserApiModel): User {
        return User(
            id = userApiModel.id,
            name = userApiModel.name,
            email = userApiModel.email,
            phone = userApiModel.phone,
            website = userApiModel.website
        )
    }

}