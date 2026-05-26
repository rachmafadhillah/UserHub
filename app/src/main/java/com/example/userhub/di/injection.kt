package com.example.userhub.di

import android.content.Context
import com.example.userhub.data.local.room.UserDatabase
import com.example.userhub.data.remote.retrofit.ApiConfig
import com.example.userhub.data.repository.UserRepository


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}