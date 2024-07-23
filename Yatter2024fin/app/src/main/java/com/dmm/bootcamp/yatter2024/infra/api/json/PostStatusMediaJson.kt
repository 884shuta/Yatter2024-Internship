package com.dmm.bootcamp.yatter2024.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostStatusMediaJson(
  @Json(name = "media_id") val mediaId: Int,
  val description: String,
)
