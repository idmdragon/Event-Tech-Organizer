package com.maungedev.data.helper

import com.maungedev.data.source.remote.FirebaseResponse
import com.maungedev.domain.utils.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        val dbSource = loadFromDB().first()
        emit(Resource.Loading(dbSource))
        if (shouldFetch(dbSource)) {
            when (val firebaseResponse = createCall().first()) {
                is FirebaseResponse.Success<RequestType> -> {
                    saveCallResult(firebaseResponse.data)
                    emitAll(loadFromDB().map {
                        Resource.Success(
                            it
                        )
                    })
                }
                is FirebaseResponse.Empty -> {
                    emitAll(loadFromDB().map {
                        Resource.Success(
                            it
                        )
                    })
                }
                is FirebaseResponse.Error -> {
                    onFetchFailed()
                    emit(
                        Resource.Error<ResultType>(
                            firebaseResponse.errorMessage
                        )
                    )
                }
            }
        } else {
            emitAll(loadFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected abstract fun loadFromDB(): Flow<ResultType?>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<FirebaseResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected open fun onFetchFailed() {}

    fun asFlow() = result

}