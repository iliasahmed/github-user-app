package com.iliasahmed.data.mapper

import com.iliasahmed.data.models.profileApiResponse
import com.iliasahmed.data.models.profileApiResponseWithNull
import com.iliasahmed.entity.ProfileEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileMapperTest {

    private val mapper = ProfileMapper()

    @Test
    fun mapFromApiResponse_should_return_ProfileEntity_with_exact_values() {
        val result: ProfileEntity = mapper.mapFromApiResponse(profileApiResponse)

        assertEquals("https://avatar.com/ilias", result.userAvatar)
        assertEquals("ilias", result.userName)
        assertEquals("Android Developer", result.about)
        assertEquals(24, result.repoCount)
        assertEquals(100, result.followerCount)
        assertEquals(50, result.followingCount)
    }

    @Test
    fun mapFromApiResponse_should_use_fallback_values_when_fields_are_null() {
        val result: ProfileEntity = mapper.mapFromApiResponse(profileApiResponseWithNull)

        assertEquals(
            "https://avatar.com/ilias",
            result.userAvatar
        )
        assertEquals("", result.userName)
        assertEquals("BIO_NOT_FOUND", result.about)
        assertEquals(0, result.repoCount)
        assertEquals(0, result.followerCount)
        assertEquals(0, result.followingCount)
    }
}
