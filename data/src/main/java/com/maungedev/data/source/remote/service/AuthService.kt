package com.maungedev.data.source.remote.service

import android.util.Log
import com.maungedev.data.constant.FirebaseConstant
import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.data.source.remote.response.UserResponse
import com.maungedev.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

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

    fun resetPassword(email: String): Flow<FirebaseResponse<Unit>> =
        flow {
            val resetPassword = auth.sendPasswordResetEmail(email).await()
            if (resetPassword!= null){
                emit(FirebaseResponse.Success(resetPassword as Unit))
            } else {
                emit(FirebaseResponse.Empty)
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)


}