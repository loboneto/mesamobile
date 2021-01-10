package br.com.loboneto.mesamobile.data

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<out T>(val code: Int, val message: String) : DataState<T>()
    data class Failure(val throwable: Throwable) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error<*> -> "Error[code=$code, message=$message]"
            is Failure -> "Error[throwable=$throwable]"
            Loading -> "Loading"
        }
    }
}

val DataState<*>.succeeded
    get() = this is DataState.Success && data != null