package br.com.loboneto.mesamobile.data.remote.news

import br.com.loboneto.mesamobile.data.Constants
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {

    @GET(Constants.NEWS)
    suspend fun fetchNews(
        @QueryMap map: HashMap<String, Any>,
        @Header("Authorization") token: String
    ): Response<NewsResult>

    @GET(Constants.HIGHLIGHTS)
    suspend fun fetchHighlights(
        @Header("Authorization") token: String
    ): Response<HighlightsResult>
}