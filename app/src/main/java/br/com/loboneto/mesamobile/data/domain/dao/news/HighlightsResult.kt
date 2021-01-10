package br.com.loboneto.mesamobile.data.domain.dao.news

import java.io.Serializable

data class HighlightsResult(
    var data: List<NewsData> = emptyList()
) : Serializable