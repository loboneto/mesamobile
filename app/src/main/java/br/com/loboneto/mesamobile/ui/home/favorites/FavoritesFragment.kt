package br.com.loboneto.mesamobile.ui.home.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.RoomState
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.databinding.FragmentFavoritesBinding
import br.com.loboneto.mesamobile.databinding.FragmentNewsBinding
import br.com.loboneto.mesamobile.ui.home.HomeViewModel
import br.com.loboneto.mesamobile.ui.home.news.NewsAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding

    private val adapter by lazy {
        FavoritesAdapter(this)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshFavorites.isEnabled = false
        binding.swipeRefreshFavorites.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.mesa))
        binding.recyclerViewFavorites.adapter = adapter
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModel.getFavorites().observe(requireActivity(), { roomState ->
            when (roomState) {
                is RoomState.Loading -> showProgress()
                is RoomState.Success -> successState(roomState.data)
                is RoomState.Failure -> errorState("Falha ao solicitar lista de notícias")
            }
        })
    }

    fun removeFavorite(favorite: Favorite) {
        viewModel.removeFavorite(favorite).observe(requireActivity(), { roomState ->
            when (roomState) {
                is RoomState.Loading -> {}
                is RoomState.Success -> adapter.removeFavorite(favorite)
                is RoomState.Failure -> errorState("Falha ao remover notícia dos favoritos")
            }
        })
    }

    private fun showProgress() {
        binding.swipeRefreshFavorites.isRefreshing = true
    }

    private fun hideProgress() {
        binding.swipeRefreshFavorites.isRefreshing = false
    }

    private fun successState(favorites: List<Favorite>) {
        hideProgress()
        adapter.setFavorites(favorites)
        if(favorites.isEmpty()) {
            showMessage("Nenhuma notícia favoritada.")
        }
    }

    private fun errorState(text: String) {
        hideProgress()
        showMessage(text)
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}
