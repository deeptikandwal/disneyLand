package com.disneyLand

sealed interface Result<out T> {
    data class Success<out T>(val value: T) : Result<T>
    data class Error(val error: Throwable) : Result<Nothing>
}
