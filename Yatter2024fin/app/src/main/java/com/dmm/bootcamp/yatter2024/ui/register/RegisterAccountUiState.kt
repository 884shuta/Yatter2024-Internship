package com.dmm.bootcamp.yatter2024.ui.register

data class RegisterAccountUiState(
  val bindingModel: RegisterAccountBindingModel,
  val isLoading: Boolean,
) {
  companion object {
    fun empty(): RegisterAccountUiState = RegisterAccountUiState(
      bindingModel = RegisterAccountBindingModel(
        userName = "",
        password = ""
      ),
      isLoading = false,
    )
  }
}
