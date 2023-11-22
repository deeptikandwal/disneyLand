package com.disneyland

sealed class Outcome<T> {
    data class Success<T>(val value: T) : Outcome<T>()
    data class Failure<T>(val error: Throwable) : Outcome<T>()
}