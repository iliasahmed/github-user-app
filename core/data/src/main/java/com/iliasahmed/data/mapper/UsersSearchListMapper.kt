package com.iliasahmed.data.mapper

import com.iliasahmed.apiresponse.UsersSearchApiResponse
import com.iliasahmed.data.utils.Mapper
import com.iliasahmed.entity.UserEntity
import javax.inject.Inject

class UsersSearchListMapper @Inject constructor() :
    Mapper<UsersSearchApiResponse, List<UserEntity>> {
    override fun mapFromApiResponse(type: UsersSearchApiResponse): List<UserEntity> {
        return type.items.map {
            UserEntity(
                id = it.id ?: 1,
                userName = it.login ?: "USERNAME_NOT_FOUND",
                userAvatar = it.avatarUrl
                    ?: "https://www.pullrequest.com/blog/github-code-review-service/images/github-logo_hub2899c31b6ca7aed8d6a218f0e752fe4_46649_1200x1200_fill_box_center_2.png"
            )
        }
    }
}