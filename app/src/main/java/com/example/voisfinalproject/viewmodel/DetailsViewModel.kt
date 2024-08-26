package com.example.voisfinalproject.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voisfinalproject.data.GitHubUserDetails
import kotlinx.coroutines.launch

import android.util.Log

class DetailsViewModel() : ViewModel() {
    private val _userDetails = mutableStateOf<GitHubUserDetails?>(null)
    val userDetails: State<GitHubUserDetails?> = _userDetails

    fun fetchUserDetails(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserDetails(username)
                _userDetails.value = response
                Log.d("DetailsViewModel", "User details fetched: ${_userDetails.value}")
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching user details", e)
            }
        }
    }
}

