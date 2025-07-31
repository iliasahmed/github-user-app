package com.iliasahmed.data.mapper

import com.iliasahmed.apiresponse.UsersApiResponse
import com.iliasahmed.data.utils.Mapper
import com.iliasahmed.entity.UserEntity
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<List<UsersApiResponse>, List<UserEntity>> {
    override fun mapFromApiResponse(type: List<UsersApiResponse>): List<UserEntity> {
        return type.map {
            UserEntity(
                userAvatar = it.avatarUrl
                    ?: "https://www.pullrequest.com/blog/github-code-review-service/images/github-logo_hub2899c31b6ca7aed8d6a218f0e752fe4_46649_1200x1200_fill_box_center_2.png",
                userName = it.login ?: "USERNAME_NOT_FOUND",
                id = it.id ?: 1
            )
        }
    }
}