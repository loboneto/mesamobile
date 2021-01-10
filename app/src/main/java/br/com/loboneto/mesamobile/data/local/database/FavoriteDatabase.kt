package br.com.loboneto.mesamobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.loboneto.mesamobile.data.local.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): RoomFavoriteDao

}
