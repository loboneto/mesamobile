package br.com.loboneto.mesamobile.data.source

import br.com.loboneto.mesamobile.data.RoomState
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.data.local.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val database: FavoriteDatabase) {

    fun setFavorite(news: Favorite) = flow {
        emit(RoomState.Loading)
        try {
            database.favoriteDao().insert(news)
            emit(RoomState.Success(Unit))
        } catch (e: Exception) {
            emit(RoomState.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun removeFavorite(news: Favorite) = flow {
        emit(RoomState.Loading)
        try {
            database.favoriteDao().remove(news)
            emit(RoomState.Success(Unit))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(RoomState.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun fetchFavorites() = flow {
        emit(RoomState.Loading)
        try {
            val favorites: List<Favorite> = database.favoriteDao().getAll()
            emit(RoomState.Success(favorites))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(RoomState.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
