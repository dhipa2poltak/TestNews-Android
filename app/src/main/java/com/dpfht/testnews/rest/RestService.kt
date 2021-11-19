package com.dpfht.testnews.rest

import com.dpfht.testnews.model.Response
import com.dpfht.testnews.model.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

  @GET("everything?q=sports&apiKey=6b799819f36740a290887d40e6f5fef8")
  suspend fun getNews(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Response

  @GET("top-headlines/sources?apiKey=6b799819f36740a290887d40e6f5fef8")
  suspend fun getSources(@Query("category") category: String): SourceResponse
}