package com.dpfht.testnews.di

import com.dpfht.testnews.BuildConfig
import com.dpfht.testnews.Config
import com.dpfht.testnews.data.api.rest.RestService
import okhttp3.CertificatePinner

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
  single { provideCertificatePinner() }
  single { provideOkHttpClient(get()) }
  single { provideRetrofit(get(), Config.API_BASE_URL) }
  single { provideRestService(get()) }
}

fun provideCertificatePinner(): CertificatePinner {
  return CertificatePinner.Builder()
    .add("newsapi.org", "sha256/UmhcQTxjIQ7hbNRvTDeFt5LId41clz5KDOcuyIP+fd4=")
    .add("newsapi.org", "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
    .add("newsapi.org", "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
    .build()
}

fun provideOkHttpClient(certificatePinner: CertificatePinner): OkHttpClient {
  return if (BuildConfig.DEBUG) {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    OkHttpClient.Builder().addInterceptor(logging)
      .certificatePinner(certificatePinner)
      .build()
  } else {
    OkHttpClient.Builder()
      .certificatePinner(certificatePinner)
      .build()
  }
}

fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
  return Retrofit.Builder()
    .baseUrl(baseUrl)
    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
}

fun provideRestService(retrofit: Retrofit): RestService {
  return retrofit.create(RestService::class.java)
}
