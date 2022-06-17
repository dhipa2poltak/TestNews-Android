package com.dpfht.testnews

import android.app.Application
import com.dpfht.testnews.framework.di.appModule
import com.dpfht.testnews.framework.di.myActivityModule
import com.dpfht.testnews.framework.di.viewModelModule
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
