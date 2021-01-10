package br.com.loboneto.mesamobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.loboneto.mesamobile.ui.home.HomeActivity
import br.com.loboneto.mesamobile.ui.auth.AuthActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LauncherActivity : AppCompatActivity() {

    private val preferences: Preferences by lazy {
        Preferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if (preferences.getToken().isEmpty()) openAuth() else openHome()
        }
    }

    private fun openAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    private fun openHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
