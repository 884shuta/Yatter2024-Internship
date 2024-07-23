package com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.AccountBindingModel

object AccountBindingModelConverter {
  fun convertToBindingModel(account: Account): AccountBindingModel =
    AccountBindingModel(
      username = account.username.value,
      displayName = account.displayName,
      note = account.note,
      avatar = account.avatar.toString(),
      header = account.header.toString(),
      followingCount = account.followingCount,
      followerCount = account.followerCount,
    )
}