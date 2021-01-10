package br.com.loboneto.mesamobile.di

import android.content.Context
import androidx.room.Room
import br.com.loboneto.mesamobile.data.Constants.BASE_URL
import br.com.loboneto.mesamobile.data.local.database.FavoriteDatabase
import br.com.loboneto.mesamobile.data.remote.auth.AuthApi
import br.com.loboneto.mesamobile.data.remote.news.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room
            .databaseBuilder(context, FavoriteDatabase::class.java, "Favorite.db")
            .build()
    }
}
