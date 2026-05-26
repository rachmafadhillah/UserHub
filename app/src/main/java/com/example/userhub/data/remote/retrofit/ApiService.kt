package com.example.userhub.data.remote.retrofit

import com.example.userhub.data.remote.response.UserResponse
import com.example.userhub.data.remote.response.UserResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("user")
    suspend fun getUser(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<UserResponseItem>
}