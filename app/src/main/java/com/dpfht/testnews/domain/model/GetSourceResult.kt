package com.dpfht.testnews.domain.model

import com.dpfht.testnews.data.model.remote.Source

data class GetSourceResult(
  val source: List<Source>
)
