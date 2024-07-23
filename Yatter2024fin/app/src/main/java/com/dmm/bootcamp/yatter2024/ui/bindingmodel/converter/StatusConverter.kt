package com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel

object StatusConverter {
  fun convertToBindingModel(statusList: List<Status>): List<StatusBindingModel> =
    statusList.map { convertToBindingModel(it) }

  fun convertToBindingModel(status: Status): StatusBindingModel =
    StatusBindingModel(
      id = status.id.value,
      displayName = status.account.displayName ?: "",
      username = status.account.username.value,
      avatar = status.account.avatar.toString(),
      content = status.content,
      attachmentMediaList = MediaConverter.convertToBindingModel(status.attachmentMediaList),
    )
}