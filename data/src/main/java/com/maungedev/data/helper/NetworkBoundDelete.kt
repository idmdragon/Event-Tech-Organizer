package com.maungedev.data.helper

import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundDelete {

    private val result: Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        when (val firebaseResponse = deleteFromNetwork().first()) {
            is FirebaseResponse.Success<Unit> -> {
                onDeleteSuccess()
                emit(Resource.Success(Unit))
            }
            is FirebaseResponse.Error -> {
                emit(
                    Resource.Error<Nothing>(
                        firebaseResponse.errorMessage
                    )
                )
            }
        }
    }

    protected abstract suspend fun deleteFromNetwork():Flow<FirebaseResponse<Unit>>

    protected abstract suspend fun onDeleteSuccess()

    fun asFlow() = result
}