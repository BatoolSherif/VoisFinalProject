package com.example.voisfinalproject.data

data class GitHubUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val url: String
)

data class UserResponse(
    val total_count: Int,
    val items: List<GitHubUser>
)