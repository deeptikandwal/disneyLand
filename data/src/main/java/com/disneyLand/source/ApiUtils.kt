package com.disneyLand.source

import com.disneyLand.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T, R> safeApiCall(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T,
    mapper: (T) -> R
): Outcome<R> {
    return withContext(ioDispatcher) {
        try {
            val response = apiCall()
            val mappedData = mapper(response)
            Outcome.Success(mappedData)
        } catch (throwable: Throwable) {
            Outcome.Failure(throwable)
        }
    }
}
