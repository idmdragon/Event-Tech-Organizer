package com.maungedev.data.source.remote.response

data class UserResponse(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val myEvent: List<String> = listOf(),
) {
}