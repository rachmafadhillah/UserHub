package com.example.userhub.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.userhub.data.local.room.UserDatabase
import com.example.userhub.data.pagging.UserPagingSource
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
//                QuotePagingSource(apiService)
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }
}