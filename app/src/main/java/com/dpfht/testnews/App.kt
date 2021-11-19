package com.dpfht.testnews

import android.app.Application
import com.dpfht.testnews.di.appModule
import com.dpfht.testnews.di.myActivityModule
import com.dpfht.testnews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@App)
      modules(listOf(appModule, myActivityModule, viewModelModule))
    }
  }
}
