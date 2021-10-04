package com.maungedev.domain.model

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val schedule: String,
    val favorite: String
) {
}