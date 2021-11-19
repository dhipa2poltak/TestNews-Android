package com.dpfht.testnews.di

import com.dpfht.testnews.Config
import com.dpfht.testnews.rest.RestService

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
  //single { provideCertificatePinner() }
  single { provideOkHttpClient(/*get()*/) }
  single { provideRetrofit(get(), Config.API_BASE_URL) }
  single { provideRestService(get()) }
  //single { provideAppRepository(get()) }
}

/*
fun provideCertificatePinner(): CertificatePinner {
  return CertificatePinner.Builder()
    .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
    .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
    .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
    .build()
}
*/

fun provideOkHttpClient(/*certificatePinner: CertificatePinner*/): OkHttpClient {
  return OkHttpClient.Builder()
    //.certificatePinner(certificatePinner)
    .build()
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
