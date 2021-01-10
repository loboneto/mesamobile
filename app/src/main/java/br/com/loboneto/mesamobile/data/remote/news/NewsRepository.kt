package br.com.loboneto.mesamobile.data.remote.news

import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import br.com.loboneto.mesamobile.data.source.MesaMobileDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository
@Inject constructor(val mesaMobileDataSource: MesaMobileDataSource) {

    suspend fun fetchNews(filter: HashMap<String, Any>, token: String): Flow<DataState<NewsResult>> =
        mesaMobileDataSource.fetchNews(filter, "Bearer $token")

    suspend fun fetchHighlights(token: String): Flow<DataState<HighlightsResult>> =
        mesaMobileDataSource.fetchHighlights("Bearer $token")
}