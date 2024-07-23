package com.dmm.bootcamp.yatter2024.domain.model

import com.dmm.bootcamp.yatter2024.common.ddd.Entity

class Media(
  id: MediaId,
  val type: String,
  val url: String,
  val description: String?,
) : Entity<MediaId>(id)
