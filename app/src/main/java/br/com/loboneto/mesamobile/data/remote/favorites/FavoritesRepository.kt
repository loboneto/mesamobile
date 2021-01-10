package br.com.loboneto.mesamobile.data.remote.favorites

import br.com.loboneto.mesamobile.data.RoomState
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavoritesRepository(private val localDataSource: LocalDataSource) {

    suspend fun saveFavorite(favorite: Favorite): Flow<RoomState<Unit>> =
        withContext(Dispatchers.Main) {
            return@withContext localDataSource.setFavorite(favorite)
        }

    suspend fun removeFavorite(favorite: Favorite): Flow<RoomState<Unit>> = withContext(Dispatchers.Main) {
        return@withContext localDataSource.removeFavorite(favorite)
    }

    suspend fun fetchFavorites(): Flow<RoomState<List<Favorite>>> = withContext(Dispatchers.Main) {
        return@withContext localDataSource.fetchFavorites()
    }
}