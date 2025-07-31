package com.iliasahmed.data.mapper

import com.iliasahmed.apiresponse.ProfileApiResponse
import com.iliasahmed.data.utils.Mapper
import com.iliasahmed.entity.ProfileEntity
import javax.inject.Inject

class ProfileMapper @Inject constructor() : Mapper<ProfileApiResponse, ProfileEntity> {
    override fun mapFromApiResponse(type: ProfileApiResponse): ProfileEntity {
        return ProfileEntity(
            userAvatar = type.avatarUrl
                ?: "https://avatar.com/ilias",
            userName = type.login ?: "",
            about = type.bio ?: "BIO_NOT_FOUND",
            repoCount = type.publicRepos ?: 0,
            followerCount = type.followers ?: 0,
            followingCount = type.following ?: 0
        )
    }
}