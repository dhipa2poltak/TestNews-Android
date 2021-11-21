package com.dpfht.testnews.net

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

  val status: String? = null,
  @SerializedName("code")
  val code: String? = null,
  @SerializedName("message")
  val message: String? = null
)
