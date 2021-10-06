package com.maungedev.data.source.remote.service

import com.maungedev.data.constant.FirebaseConstant
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class AuthService : FirebaseService() {

    fun signUp(email: String, password: String, user: User): Flow<FirebaseResponse<UserResponse>> =
        flow {
            createUserWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            setDocument<User, UserResponse>(
                                FirebaseConstant.FirebaseCollection.USER,
                                response.data,
                                user.copy(
                                    uid = response.data
                                )
                            )
                        )
                    }
                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                    }
                    FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                    }
                }

            }
        }

    fun signIn(email: String, password: String): Flow<FirebaseResponse<UserResponse>> =
        flow {
            signInWithEmailAndPassword(email, password).collect { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        emitAll(
                            getDocument<UserResponse>(
                                FirebaseConstant.FirebaseCollection.USER,
                                response.data
                            )
                        )
                    }
                    is FirebaseResponse.Error -> {
                        emit(FirebaseResponse.Error(response.errorMessage))
                    }
                    FirebaseResponse.Empty -> {
                        emit(FirebaseResponse.Empty)
                    }
                }

            }
        }
}