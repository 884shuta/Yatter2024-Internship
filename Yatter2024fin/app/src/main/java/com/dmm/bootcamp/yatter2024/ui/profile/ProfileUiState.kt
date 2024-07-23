package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.bindingmodel.AccountBindingModel
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel

data class ProfileUiState(
  val accountBindingModel: AccountBindingModel?,
  val statusList: List<StatusBindingModel>,
  val isLoading: Boolean,
  val mine: Boolean,
) {
  companion object {
    fun empty(): ProfileUiState = ProfileUiState(
      accountBindingModel = null,
      statusList = emptyList(),
      isLoading = false,
      mine = false,
    )
  }
}
