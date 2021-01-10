package br.com.loboneto.mesamobile.ui.home.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.loboneto.mesamobile.BR
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import br.com.loboneto.mesamobile.databinding.AdapterHighlightsBinding
import com.bumptech.glide.Glide

class HighlightsAdapter : RecyclerView.Adapter<HighlightsAdapter.ViewHolder>() {

    private var highlights: List<NewsData> = emptyList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(highlights[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewHolder = ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_highlights,
                parent,
                false
            )
        )

        val width = parent.width

        val params = viewHolder.itemView.layoutParams

        params.width = (width * 0.8).toInt()

        viewHolder.itemView.layoutParams = params

        return viewHolder
    }

    class ViewHolder(private val binding: AdapterHighlightsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(news: NewsData) {

            val view = binding.root

            Glide.with(binding.root.context)
                .load(news.image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageViewNews)

            binding.setVariable(BR.news, news)
            binding.executePendingBindings()

            view.setOnClickListener {

            }
        }
    }

    fun setHighlights(highlightsResult: HighlightsResult) {
        highlights = highlightsResult.data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = highlights.size
}
