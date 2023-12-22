package com.disneyLand

sealed class Outcome<out T> {
    data class Success<out T>(val value: T) : Outcome<T>()
    data class Failure<T>(val error: Throwable) : Outcome<T>()
}
