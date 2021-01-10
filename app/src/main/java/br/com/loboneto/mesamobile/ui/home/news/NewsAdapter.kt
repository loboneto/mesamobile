package br.com.loboneto.mesamobile.ui.home.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.loboneto.mesamobile.BR
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.dao.News
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsPagination
import br.com.loboneto.mesamobile.databinding.AdapterNewsBinding
import br.com.loboneto.mesamobile.util.verifyUrl
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val fragment: NewsFragment) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(), Filterable {

    private var news = ArrayList<News>()

    private lateinit var pagination: NewsPagination

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(news[position], fragment)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_news,
                parent,
                false
            )
        )

    class ViewHolder(private val binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(news: News, fragment: NewsFragment) {

            val view = binding.root

            Glide.with(view.context)
                .load(news.newsData.image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageViewNews)

            if (news.favorite) {
                binding.imageViewFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context,
                        R.drawable.ic_favorite_filled
                    )
                )
            } else {
                binding.imageViewFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context,
                        R.drawable.ic_favorite_outline
                    )
                )
            }

            binding.setVariable(BR.news, news)
            binding.executePendingBindings()

            binding.cardViewFavorite.setOnClickListener {
                if (news.favorite) {
                    removeFavorite(news.newsData, fragment)
                } else {
                    saveFavorite(news.newsData, fragment)
                }
            }

            view.setOnClickListener {
                openNews(news.newsData, fragment)
            }
        }

        private fun openNews(news: NewsData, fragment: NewsFragment) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url.verifyUrl()))
            fragment.context?.startActivity(intent)
        }

        private fun removeFavorite(news: NewsData, fragment: NewsFragment) {
            fragment.removeFavorite(news, news.toFavorite())
        }

        private fun saveFavorite(news: NewsData, fragment: NewsFragment) {
            fragment.saveFavorite(news, news.toFavorite())
        }
    }

    fun setNews(uiNews: List<News>, uiPagination: NewsPagination) {
        if (isFirstOrOnlyPage(uiPagination)) {
            news.clear()
            news.addAll(uiNews.sortedBy { it.newsData.published_at })
            pagination = uiPagination
            notifyDataSetChanged()
        } else {
            val size = news.size
            val newSize = uiNews.size
            news.addAll(uiNews)
            pagination = uiPagination
            fragment.binding.recyclerViewNews.scrollToPosition(size + 1)
            notifyItemRangeChanged(size, newSize)
        }
    }

    fun setFavorite(new: NewsData) {
        val removedNews = News(new, false)
        val index = news.indexOf(removedNews)
        news[index] = News(new, true)
        notifyItemChanged(index)
        Toast.makeText(fragment.context, "Notícia adicionada aos favoritos", Toast.LENGTH_LONG)
            .show()
    }

    fun unsetFavorite(new: NewsData) {
        val removedNews = News(new, true)
        val index = news.indexOf(removedNews)
        news[index] = News(new, false)
        notifyItemChanged(index)
        Toast.makeText(fragment.context, "Notícia removida dos favoritos", Toast.LENGTH_LONG).show()
    }

    private fun isFirstOrOnlyPage(pagination: NewsPagination): Boolean {
        return pagination.total_pages == 1 || pagination.current_page == 1 || pagination.current_page == pagination.total_pages
    }

    fun isLastPage(): Boolean = pagination.current_page + 1 > pagination.total_pages

    override fun getItemCount(): Int = news.size

    override fun getFilter(): Filter = filteredList

    private val filteredList: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {

            var filteredNews = ArrayList<News>()

            if (constraint.isNullOrEmpty()) {
                filteredNews = news
            } else {
                val string = constraint.toString().toLowerCase(Locale.getDefault())
                news.forEach { local ->
                    val localName = local.newsData.title.toLowerCase(Locale.getDefault())
                    if (localName.contains(string)) {
                        filteredNews.add(local)
                    } else {
                        filteredNews.remove(local)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredNews
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            try {
                news.clear()
                news.addAll(results.values as List<News>)
                notifyDataSetChanged()
            } catch (e: Exception) {
                notifyDataSetChanged()
            }
        }
    }
}
