package br.com.loboneto.mesamobile.ui.home.news

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.RoomState
import br.com.loboneto.mesamobile.data.dao.News
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsData
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import br.com.loboneto.mesamobile.data.local.Favorite
import br.com.loboneto.mesamobile.databinding.FragmentNewsBinding
import br.com.loboneto.mesamobile.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news),
    View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: FragmentNewsBinding

    private lateinit var searchView: SearchView

    private val newsAdapter by lazy {
        NewsAdapter(this)
    }

    private val highlightsAdapter = HighlightsAdapter()

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        observeFavorites()
//        observeHighlights()
//        observeNews()
    }

    private fun setUpView() {
        binding.fabFilter.setOnClickListener(this)
        binding.swipeRefreshNews.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.mesa
            )
        )
        binding.recyclerViewNews.adapter = newsAdapter

        binding.recyclerViewHighlights.adapter = highlightsAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewHighlights)


        binding.recyclerViewNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.canScrollVertically(-1)) {
                    binding.fabFilter.shrink()
                } else {
                    binding.fabFilter.extend()
                }

                if (!recyclerView.canScrollVertically(1)) {
                    if (!newsAdapter.isLastPage()) {
                        val filter = viewModel.getFilter()
                        filter.currentPage = filter.currentPage + 1
                        viewModel.setFilter(filter)
                    }
                }
            }
        })
    }

    private fun observeFavorites() {
        viewModel.getFavorites().observe(requireActivity()) { roomState ->
            when (roomState) {
                is RoomState.Loading -> showProgress()
                is RoomState.Success -> {
                    binding.swipeRefreshNews.isEnabled = false
                    viewModel.getFavorites().removeObservers(this)
                    observeHighlights()
                    observeNews(roomState.data)
                }
                is RoomState.Failure -> {
                    hideProgress()
                    showMessage("Algo aconteceu, tente novamente!")
                }
            }
        }
    }

    private fun observeHighlights() {
        viewModel.getHighlights().observe(requireActivity(), { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                }
                is DataState.Success -> successState(dataState.data)
                is DataState.Error -> errorState("${dataState.code} - ${dataState.message}")
                is DataState.Failure -> errorState("Falha ao solicitar lista de destaques")
            }
        })
    }

    private fun observeNews(favorites: List<Favorite>) {
        viewModel.news.observe(requireActivity(), { dataState ->
            when (dataState) {
                is DataState.Loading -> showProgress()
                is DataState.Success -> successState(dataState.data, favorites)
                is DataState.Error -> errorState("${dataState.code} - ${dataState.message}")
                is DataState.Failure -> errorState("Falha ao solicitar lista de notícias")
            }
        })
    }

    fun saveFavorite(news: NewsData, favorite: Favorite) {
        viewModel.saveFavorite(favorite).observe(this) { roomState ->
            when (roomState) {
                is RoomState.Loading -> { }
                is RoomState.Success -> newsAdapter.setFavorite(news)
                is RoomState.Failure -> showMessage("Falha ao favoritar notícia")
            }
        }
    }

    fun removeFavorite(news: NewsData, favorite: Favorite) {
        viewModel.removeFavorite(favorite).observe(this) { roomState ->
            when (roomState) {
                is RoomState.Loading -> { }
                is RoomState.Success -> newsAdapter.unsetFavorite(news)
                is RoomState.Failure -> showMessage("Falha ao remover notícia das favoritas")
            }
        }
    }

    private fun showProgress() {
        binding.swipeRefreshNews.isRefreshing = true
    }

    private fun hideProgress() {
        binding.swipeRefreshNews.isRefreshing = false
    }

    private fun successState(news: HighlightsResult) {
        highlightsAdapter.setHighlights(news)
    }

    private fun successState(news: NewsResult, favorites: List<Favorite>) {
        hideProgress()
        val uiNews = ArrayList<News>()
        news.data.forEach { new ->
            val uiNew = News(new)
            favorites.forEach { favorite ->
                if (new.title == favorite.title) {
                    uiNew.favorite = true
                }
            }
            uiNews.add(uiNew)
        }
        newsAdapter.setNews(uiNews, news.pagination)
    }

    private fun errorState(text: String) {
        hideProgress()
        showMessage(text)
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_action_search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                (binding.recyclerViewNews.adapter as NewsAdapter).filter.filter(
                    newText
                )
                return false
            }
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabFilter -> {
                val bottomSheet = NewsFilterBottomSheet()
                val bundle = Bundle()
                bundle.putSerializable("filter", viewModel.getFilter())
                bottomSheet.arguments = bundle
                bottomSheet.show(childFragmentManager, TAG)
            }
        }
    }

    companion object {
        const val TAG = "NEWS_FRAG"
    }

    override fun onRefresh() {
        observeFavorites()
    }
}
