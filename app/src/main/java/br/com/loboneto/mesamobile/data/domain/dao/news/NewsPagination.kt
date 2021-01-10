package br.com.loboneto.mesamobile.data.domain.dao.news

import java.io.Serializable

data class NewsPagination(
    val current_page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int
) : Serializable