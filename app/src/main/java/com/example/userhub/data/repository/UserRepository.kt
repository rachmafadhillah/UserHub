package com.example.userhub.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.userhub.data.local.room.UserDatabase
import com.example.userhub.data.pagging.UserRemoteMediator
import com.example.userhub.data.remote.response.UserResponseItem
import com.example.userhub.data.remote.retrofit.ApiService

class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getUser(): LiveData<PagingData<UserResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
//                UserPagingSource(apiService)
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }

    suspend fun addUser(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        city: String,
        gender: Int
    ): Result<UserResponseItem> {
        return try {
            val response = apiService.addUser(name, address, email, phoneNumber, city, gender)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}