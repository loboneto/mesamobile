package br.com.loboneto.mesamobile.data

sealed class RoomState<out R> {

    data class Success<out T>(val data: T) : RoomState<T>()
    data class Failure(val throwable: Throwable) : RoomState<Nothing>()
    object Loading : RoomState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[throwable=$throwable]"
            Loading -> "Loading"
        }
    }
}

val RoomState<*>.succeeded
    get() = this is RoomState.Success && data != null