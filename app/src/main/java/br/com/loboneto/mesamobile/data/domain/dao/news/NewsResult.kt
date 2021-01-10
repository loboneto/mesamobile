package br.com.loboneto.mesamobile.data.domain.dao.news

import java.io.Serializable

data class NewsResult(
    var pagination: NewsPagination,
    var data: List<NewsData> = emptyList()
) : Serializable