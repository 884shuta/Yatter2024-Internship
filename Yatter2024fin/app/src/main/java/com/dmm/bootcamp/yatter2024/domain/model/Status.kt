package com.dmm.bootcamp.yatter2024.domain.model

import com.dmm.bootcamp.yatter2024.common.ddd.Entity

class Status(
  id: StatusId,
  val account: Account,
  val content: String,
  val attachmentMediaList: List<Media>
) : Entity<StatusId>(id)
