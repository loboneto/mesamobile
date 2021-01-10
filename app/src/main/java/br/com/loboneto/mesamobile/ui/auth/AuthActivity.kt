package br.com.loboneto.mesamobile.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.loboneto.mesamobile.R
import br.com.loboneto.mesamobile.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_sign_in))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun showProgress() {
        //SystemUtil.hideKeyboard(this)
        binding.frameLoading.visibility = View.VISIBLE
    }

    fun hideProgress() {
        binding.frameLoading.visibility = View.GONE
    }

    private fun isLoading(): Boolean {
        return binding.frameLoading.visibility == View.VISIBLE
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (isLoading()) false else NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        )
    }
}
