package br.com.loboneto.mesamobile.ui.home.favorites

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.loboneto.mesamobile.BR
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.databinding.AdapterFavoritesBinding
import com.bumptech.glide.Glide

class FavoritesAdapter(private val fragment: FavoritesFragment) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var favorites = ArrayList<Favorite>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(favorites[position], fragment)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_favorites,
                parent,
                false
            )
        )

    class ViewHolder(private val binding: AdapterFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(favorite: Favorite, fragment: FavoritesFragment) {

            val view = binding.root

            Glide.with(view.context)
                .load(favorite.image_url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageViewNews)

            binding.setVariable(BR.favorite, favorite)
            binding.executePendingBindings()

            binding.cardViewFavorite.setOnClickListener { removeFavorite(favorite, fragment) }

            view.setOnClickListener { openFavorite(favorite, fragment) }
        }

        private fun openFavorite(favorite: Favorite, fragment: FavoritesFragment) {
            val url =
                if (!favorite.url.startsWith("http://") && !favorite.url.startsWith("https://")) {
                    "http://${favorite.url}"
                } else {
                    favorite.url
                }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            fragment.context?.startActivity(intent)
        }

        private fun removeFavorite(favorite: Favorite, fragment: FavoritesFragment) {
            fragment.removeFavorite(favorite)
        }
    }

    fun setFavorites(favorites: List<Favorite>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    fun removeFavorite(favorite: Favorite) {
        favorites.remove(favorite)
        notifyDataSetChanged()
        if (favorites.isEmpty()) {
            Toast.makeText(
                fragment.context,
                "Nenhuma notícia favoritada.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount(): Int = favorites.size
}
