package br.com.loboneto.mesamobile.ui.home

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import br.com.loboneto.mesamobile.Preferences
import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dao.UserDAO
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsFilter
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.data.remote.favorites.FavoritesRepository
import br.com.loboneto.mesamobile.data.remote.news.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject
constructor(
    @ApplicationContext context: Context,
    private val newsRepository: NewsRepository,
    private val favoritesRepository: FavoritesRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val preferences = Preferences(context)

    fun getUserData(): UserDAO =
        UserDAO(preferences.getName(), preferences.getEmail())

    private var _filter = MutableLiveData<NewsFilter>()

    fun setFilter(filter: NewsFilter) {
        _filter.value = filter
    }

    fun getFilter(): NewsFilter {
        return _filter.value ?: NewsFilter()
    }

    val news: LiveData<DataState<NewsResult>> = Transformations.switchMap(_filter) {
        getNews(it.toHash())
    }

    private fun getNews(filter: HashMap<String, Any>) = liveData {
        emitSource(newsRepository.fetchNews(filter, preferences.getToken()).asLiveData())
    }

    fun getHighlights() = liveData {
        emitSource(newsRepository.fetchHighlights(preferences.getToken()).asLiveData())
    }

    fun getFavorites() = liveData {
        emitSource(favoritesRepository.fetchFavorites().asLiveData())
    }

    fun saveFavorite(favorite: Favorite) = liveData {
        emitSource(favoritesRepository.saveFavorite(favorite).asLiveData())
    }

    fun removeFavorite(favorite: Favorite) = liveData {
        emitSource(favoritesRepository.removeFavorite(favorite).asLiveData())
    }

    init {
        _filter.value = NewsFilter()
    }
}
