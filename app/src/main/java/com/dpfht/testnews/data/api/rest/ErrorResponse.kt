package com.dpfht.testnews.data.api.rest

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

  val status: String? = null,
  @SerializedName("code")
  val code: String? = null,
  @SerializedName("message")
  val message: String? = null
)
