package com.dpfht.testnews.data.api.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object UnsafeOkHttpClient {

  fun getUnsafeOkHttpClient(): OkHttpClient {
    return try {
      // Create a trust manager that does not validate certificate chains
      val trustAllCerts =
        arrayOf<TrustManager>(
          object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
              chain: Array<X509Certificate?>?,
              authType: String?
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
              chain: Array<X509Certificate?>?,
              authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
              return arrayOf()
            }
          }
        )

      // Install the all-trusting trust manager
      val sslContext = SSLContext.getInstance("SSL")
      sslContext.init(null, trustAllCerts, SecureRandom())
      // Create an ssl socket factory with our all-trusting manager
      val sslSocketFactory = sslContext.socketFactory
      val builder = OkHttpClient.Builder()
      builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
      builder.hostnameVerifier { _, _ -> true }

      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

      builder.addInterceptor(httpLoggingInterceptor)
      builder.addInterceptor(AuthInterceptor())
      builder.build()
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}
