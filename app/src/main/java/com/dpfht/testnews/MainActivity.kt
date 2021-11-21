package com.dpfht.testnews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.testnews.features.category.CategoryActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_main)

    val isTest = false
    if (isTest) {
      GlobalScope.launch {
        testHit()
      }
    } else {
      startActivity(Intent(this, CategoryActivity::class.java))
      finish()
    }
  }

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
}
