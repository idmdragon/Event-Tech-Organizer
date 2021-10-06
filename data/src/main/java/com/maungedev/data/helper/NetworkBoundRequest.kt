package com.maungedev.data.helper

import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

abstract class NetworkBoundRequest<RequestType> {
    private val result: Flow<Resource<Unit>> = flow {
        preRequest()
        emit(Resource.Loading())
        when (val firebaseResponse = createCall().first()) {
            is FirebaseResponse.Success<RequestType> -> {
                saveCallResult(firebaseResponse.data)
                emit(Resource.Success(null))
            }
            is FirebaseResponse.Error -> {
                emit(
                    Resource.Error<Unit>(
                    firebaseResponse.errorMessage
                    )
                )
            }
        }
    }

    protected open suspend fun preRequest(){}

    protected abstract suspend fun createCall(): Flow<FirebaseResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)




    fun asFlow() = result
}