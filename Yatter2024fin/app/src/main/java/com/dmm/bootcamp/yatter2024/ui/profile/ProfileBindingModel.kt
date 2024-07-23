package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.bindingmodel.AccountBindingModel
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel

data class ProfileBindingModel(
  val accountBindingModel: AccountBindingModel,
  val homeStatusList: List<StatusBindingModel>,
)
