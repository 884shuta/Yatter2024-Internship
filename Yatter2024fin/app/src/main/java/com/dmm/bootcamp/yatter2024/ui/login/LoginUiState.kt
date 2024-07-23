package com.dmm.bootcamp.yatter2024.ui.login

internal data class LoginUiState(
  val loginBindingModel: LoginBindingModel,
  val validUsername: Boolean,
  val validPassword: Boolean,
  val isLoading: Boolean,
) {
  companion object {
    fun empty(): LoginUiState = LoginUiState(
      loginBindingModel = LoginBindingModel(
        username = "",
        password = ""
      ),
      validUsername = false,
      validPassword = false,
      isLoading = false,
    )
  }

  val isEnableLogin: Boolean = validUsername && validPassword
}
