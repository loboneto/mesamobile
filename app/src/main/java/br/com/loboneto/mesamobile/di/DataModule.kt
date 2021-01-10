package br.com.loboneto.mesamobile.di

import android.content.Context
import androidx.room.Room
import br.com.loboneto.mesamobile.data.local.database.FavoriteDatabase
import br.com.loboneto.mesamobile.data.source.MesaMobileDataSource
import br.com.loboneto.mesamobile.data.remote.auth.AuthApi
import br.com.loboneto.mesamobile.data.remote.auth.AuthRepository
import br.com.loboneto.mesamobile.data.remote.favorites.FavoritesRepository
import br.com.loboneto.mesamobile.data.remote.news.NewsApi
import br.com.loboneto.mesamobile.data.remote.news.NewsRepository
import br.com.loboneto.mesamobile.data.source.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(database: FavoriteDatabase): LocalDataSource =
        LocalDataSource(database)

    @Singleton
    @Provides
    fun provideFavoriteRepository(localDataSource: LocalDataSource): FavoritesRepository =
        FavoritesRepository(localDataSource)


    @Singleton
    @Provides
    fun provideMesaMobileDataSource(authApi: AuthApi, newsApi: NewsApi): MesaMobileDataSource =
        MesaMobileDataSource(authApi, newsApi)

    @Singleton
    @Provides
    fun provideAuthRepository(mesaMobileDataSource: MesaMobileDataSource): AuthRepository =
        AuthRepository(mesaMobileDataSource)

    @Singleton
    @Provides
    fun provideNewsRepository(mesaMobileDataSource: MesaMobileDataSource): NewsRepository =
        NewsRepository(mesaMobileDataSource)

}
