package me.rerere.rainmusic.util

import androidx.compose.runtime.Composable

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

    val notLoaded: Boolean
        get() = this is Empty || this is Error

    /**
     * 可视化DataState
     */
    @Composable
    inline fun Display(
        empty: @Composable () -> Unit = {},
        loading: @Composable () -> Unit = {},
        error: @Composable (Exception) -> Unit = {},
        success: @Composable (T) -> Unit
    ) {
        when(this){
            is Success -> success(read())
            is Error -> error(exception)
            is Loading -> loading()
            is Empty -> empty()
        }
    }
}