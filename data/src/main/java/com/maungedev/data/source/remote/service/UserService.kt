package com.maungedev.data.source.remote.service

import com.maungedev.data.constant.FirebaseConstant.FirebaseCollection.USER
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow

class UserService : FirebaseService() {

    fun getCurrentUser(): Flow<FirebaseResponse<UserResponse>> =
        getDocument(USER,getCurrentUserId() as String)

}