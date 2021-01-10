package br.com.loboneto.mesamobile.data.domain.dao.news

import java.io.Serializable

data class NewsFilter(
    var currentPage: Int = 1,
    var per_page: Int = 20,
    var published_at: String? = null
) : Serializable  {

    fun dateFilter(date: String): NewsFilter {
        currentPage = 1
        published_at = date
        return this
    }

    fun toHash(): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        map["current_page"] = currentPage
        map["per_page"] = per_page
        published_at?.also { map["published_at"] = it }
        return map
    }
}
