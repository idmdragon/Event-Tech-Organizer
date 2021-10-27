package com.maungedev.data.source.remote.service

import com.maungedev.data.constant.FirebaseConstant
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow

class UserService : FirebaseService() {

    fun getUser(id:String):Flow<FirebaseResponse<UserResponse>> =
        getDocument(
            FirebaseConstant.FirebaseCollection.USER,
            id
        )

    fun updateUsername(username: String,userId: String): Flow<FirebaseResponse<UserResponse>> =
        updateFieldInDocument<User, UserResponse>(
            FirebaseConstant.FirebaseCollection.USER,
            userId,
            FirebaseConstant.Field.USERNAME,
            username
        )

    fun logout() : Unit =
        signOut()
}