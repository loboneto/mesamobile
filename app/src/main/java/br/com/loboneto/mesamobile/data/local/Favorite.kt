package br.com.loboneto.mesamobile.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.loboneto.mesamobile.data.dao.News
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import java.io.Serializable

@Entity
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "published_at") val published_at: String,
    @ColumnInfo(name = "highlight") val highlight: Boolean,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image_url") val image_url: String
) : Serializable {
    fun date(): String {
        val split = published_at.split("-")
        val year = split[0]
        val month = split[1]
        val day = split[2].subSequence(0, 2)
        return "$day/$month/$year"
    }

    fun toNews(): News {
        return News(
            newsData = NewsData(
                title = title,
                description = description,
                content = content,
                author = author,
                published_at = published_at,
                highlight = highlight,
                url = url,
                image_url = image_url
            )
        )
    }
}
