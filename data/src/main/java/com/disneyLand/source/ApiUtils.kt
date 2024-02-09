package com.disneyLand.source

import com.disneyLand.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T, R> safeApiCall(
    ioDispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
    mapper: (T) -> R
): Result<R> {
    return withContext(ioDispatcher) {
        try {
            val response = apiCall()
            val mappedData = mapper(response)
            Result.Success(mappedData)
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }
}
