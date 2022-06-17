package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.Config
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor: Interceptor {
  override fun intercept(chain: Chain): Response {
    val original = chain.request()
    val originalHttpUrl = original.url

    val url = originalHttpUrl.newBuilder()
      .addQueryParameter("apiKey", Config.API_KEY)
      .build()

    val requestBuilder = original.newBuilder()
      .url(url)
      .method(original.method, original.body)

    val request = requestBuilder.build()

    return chain.proceed(request)
  }
}
