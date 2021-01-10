package br.com.loboneto.mesamobile.data.domain.dao.news

import br.com.loboneto.mesamobile.data.local.Favorite
import java.io.Serializable

data class NewsData(
    val title: String,
    val description: String,
    val content: String,
    val author: String,
    val published_at: String,
    val highlight: Boolean,
    val url: String,
    val image_url: String
) : Serializable {

    fun toFavorite(): Favorite {
        return Favorite(
            title = this.title,
            description = this.description,
            content = this.content,
            author = this.author,
            published_at = this.published_at,
            highlight = this.highlight,
            url = this.url,
            image_url = this.image_url
        )
    }

    fun date(): String {
        val split = published_at.split("-")
        val year = split[0]
        val month = split[1]
        val day = split[2].subSequence(0, 2)
        return "$day/$month/$year"
    }

    override fun equals(other: Any?): Boolean {
        return if(other is Favorite) {
            title == other.title &&
                    description == other.description &&
                    content == other.content &&
                    author == other.author &&
                    published_at == other.published_at &&
                    highlight == other.highlight &&
                    url == other.url &&
                    image_url == other.image_url
        } else {
            super.equals(other)
        }
    }
}