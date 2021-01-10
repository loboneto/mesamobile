package br.com.loboneto.mesamobile.data.dao

import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsPagination
import java.io.Serializable

data class News(
    val newsData: NewsData,
    var favorite: Boolean = false
) : Serializable