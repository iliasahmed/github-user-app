package com.iliasahmed.apiresponse

import com.google.gson.annotations.SerializedName

data class UsersSearchApiResponse(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val items: List<UsersApiResponse>
)