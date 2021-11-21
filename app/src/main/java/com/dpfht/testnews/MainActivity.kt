package com.dpfht.testnews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dpfht.testnews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.demo_nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    NavigationUI.setupActionBarWithNavController(this, navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = Navigation.findNavController(this, R.id.demo_nav_host_fragment)
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  /*
  private fun testHit() {
    val hostname = "newsapi.org"
    val certificatePinner = CertificatePinner.Builder()
      .add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
      .build()
    val client = OkHttpClient.Builder()
      .certificatePinner(certificatePinner)
      .build()

    val request: Request = Request.Builder()
      .url("https://$hostname")
      .build()
    client.newCall(request).execute()
  }
  */
}
