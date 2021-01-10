package br.com.loboneto.mesamobile.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.loboneto.mesamobile.data.local.Favorite

@Dao
interface RoomFavoriteDao {
    @Insert(onConflict = REPLACE)
    fun insert(favorite: Favorite)

    @Delete
    fun remove(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getAll(): List<Favorite>
}
