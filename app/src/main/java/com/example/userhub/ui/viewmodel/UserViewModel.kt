package com.example.userhub.ui.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.userhub.data.remote.response.UserResponseItem
import com.example.userhub.data.repository.UserRepository
import com.example.userhub.di.Injection
import kotlinx.coroutines.launch

class UserViewModel(userRepository: UserRepository) : ViewModel() {

    val user: LiveData<PagingData<UserResponseItem>> =
        userRepository.getUser().cachedIn(viewModelScope)

}