package com.dpfht.testnews.rest

import com.dpfht.testnews.Config
import com.dpfht.testnews.model.ArticlesResponse
import com.dpfht.testnews.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

  @GET("top-headlines?apiKey=${Config.API_KEY}")
  suspend fun getArticles(
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    @Query("sources") sources: String,
    @Query("q") query: String?
  ): ArticlesResponse

  @GET("top-headlines/sources?apiKey=${Config.API_KEY}")
  suspend fun getSources(@Query("category") category: String): SourceResponse
}
