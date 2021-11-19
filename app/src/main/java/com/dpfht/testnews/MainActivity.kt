package com.dpfht.testnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dpfht.testnews.features.category.CategoryActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setContentView(R.layout.activity_main)

    startActivity(Intent(this, CategoryActivity::class.java))
    finish()
  }
}
