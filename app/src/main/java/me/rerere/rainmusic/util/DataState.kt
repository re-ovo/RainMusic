package me.rerere.rainmusic.util

sealed class DataState<out T> {
    object Empty : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    data class Error(
        val exception: Exception
    ) : DataState<Nothing>()

    data class Success<T>(
        val data: T
    ) : DataState<T>()

    fun read(): T = (this as Success<T>).data

    fun readSafely(): T? = if (this is Success<T>) read() else null
}