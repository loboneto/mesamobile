package br.com.loboneto.mesamobile.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsFilter
import br.com.loboneto.mesamobile.ui.home.news.NewsFilterBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NewsFilterBottomSheet.OnResultListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_news, R.id.navigation_favorites)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

//    fun showProgress() {
//        //SystemUtil.hideKeyboard(this)
//        constraintLoading.visibility = View.VISIBLE
//    }
//
//    fun hideProgress() {
//        constraintLoading.visibility = View.GONE
//    }
//
//    private fun isLoading(): Boolean {
//        return constraintLoading.visibility == View.VISIBLE
//    }

//    override fun onBackPressed() {
//        onSupportNavigateUp()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return if (isLoading()) false else NavigationUI.navigateUp(
//            navController,
//            appBarConfiguration
//        )
//    }

    override fun setResultPass(filter: NewsFilter) {
        viewModel.setFilter(filter)
    }
}