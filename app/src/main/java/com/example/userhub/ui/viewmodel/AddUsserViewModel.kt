package com.example.userhub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userhub.data.remote.response.UserResponseItem
import com.example.userhub.data.repository.UserRepository
import kotlinx.coroutines.launch

class AddUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _addResult = MutableLiveData<Result<UserResponseItem>>()
    val addResult: LiveData<Result<UserResponseItem>> = _addResult

    fun addUser(
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        city: String,
        gender: Int
    ) {
        viewModelScope.launch {
            val result = userRepository.addUser(
                name = name,
                address = address,
                email = email,
                phoneNumber = phoneNumber,
                city = city,
                gender = gender
            )
            _addResult.postValue(result)
        }
    }
}