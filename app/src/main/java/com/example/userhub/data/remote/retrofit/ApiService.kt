package com.example.userhub.data.remote.retrofit

import com.example.userhub.data.remote.response.UserResponseItem
import retrofit2.http.*

interface ApiService {
    @GET("user")
    suspend fun getUser(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<UserResponseItem>

    @FormUrlEncoded
    @POST("user")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("email") email: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("city") city: String,
        @Field("gender") gender: Int
    ): UserResponseItem
}