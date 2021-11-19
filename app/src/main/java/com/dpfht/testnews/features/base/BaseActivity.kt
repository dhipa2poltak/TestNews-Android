package com.dpfht.testnews.features.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

open class BaseActivity: AppCompatActivity() {

  lateinit var prgDialog: AlertDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    prgDialog = get { parametersOf(this) }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        onBackPressed()
      }
    }

    return true
  }
}