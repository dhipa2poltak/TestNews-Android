package com.dpfht.testnews.data.model.remote

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
  @SerializedName("status")
  val status: String,
  @SerializedName("totalResults")
  val totalResults: Int,
  @SerializedName("articles")
  val articles: List<Article>
)

data class Article(
  @SerializedName("source")
  val source: PairSource,
  @SerializedName("author")
  val author: String?,
  @SerializedName("title")
  val title: String,
  @SerializedName("description")
  val description: String,
  @SerializedName("url")
  val url: String,
  @SerializedName("urlToImage")
  val image: String,
  @SerializedName("publishedAt")
  val publishedAt: String,
  @SerializedName("content")
  val content: String
)

data class PairSource(
  @SerializedName("id")
  val id: String,
  @SerializedName("name")
  val name: String
)