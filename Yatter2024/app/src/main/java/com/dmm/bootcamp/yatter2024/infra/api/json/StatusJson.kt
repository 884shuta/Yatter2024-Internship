package com.dmm.bootcamp.yatter2024.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatusJson(
  @Json(name = "id") val id: String,
  @Json(name = "account") val account: AccountJson,
  @Json(name = "content") val content: String?,
  @Json(name = "create_at") val createAt: String,
  @Json(name = "media_attachments") val attachmentMediaList: List<MediaJson>
)
