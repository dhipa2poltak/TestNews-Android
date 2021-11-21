package com.dpfht.testnews.rest

import com.dpfht.testnews.model.ArticlesResponse
import com.dpfht.testnews.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

  /*
  @GET("everything?apiKey=6b799819f36740a290887d40e6f5fef8")
  suspend fun getArticles(
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    @Query("sources") sources: String
  ): Response
  */

  @GET("top-headlines?apiKey=6b799819f36740a290887d40e6f5fef8")
  suspend fun getArticles(
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int,
    @Query("sources") sources: String,
    @Query("q") query: String?
  ): ArticlesResponse

  @GET("top-headlines/sources?apiKey=6b799819f36740a290887d40e6f5fef8")
  suspend fun getSources(@Query("category") category: String): SourceResponse
}
