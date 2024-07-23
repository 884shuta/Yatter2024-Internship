package com.dmm.bootcamp.yatter2024.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.register.RegisterAccountDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
  private val loginUseCase: LoginUseCase,
) : ViewModel() {
  private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())
  val uiState: StateFlow<LoginUiState> = _uiState

  private val _destination = MutableStateFlow<Destination?>(null)
  val destination: StateFlow<Destination?> = _destination.asStateFlow()

  fun onChangedUsername(username: String) {
    val snapshotBindingModel = uiState.value.loginBindingModel
    _uiState.update {
      it.copy(
        validUsername = Username(username).validate(),
        loginBindingModel = snapshotBindingModel.copy(
          username = username
        )
      )
    }
  }

  fun onChangedPassword(password: String) {
    val snapshotBindingModel = uiState.value.loginBindingModel
    _uiState.update {
      it.copy(
        validPassword = Password(password).validate(),
        loginBindingModel = snapshotBindingModel.copy(
          password = password
        )
      )
    }
  }

  fun onClickLogin() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val snapBindingModel = uiState.value.loginBindingModel
      when (
        val result =
          loginUseCase.execute(
            Username(snapBindingModel.username),
            Password(snapBindingModel.password),
          )
      ) {
        is LoginUseCaseResult.Success -> {
          _destination.value = PublicTimelineDestination {
            popUpTo(LoginDestination().route) {
              inclusive = true
            }
          }
        }

        is LoginUseCaseResult.Failure -> {
          // エラー処理
          println(result)
        }
      }

      _uiState.update { it.copy(isLoading = true) }
    }
  }

  fun onClickRegister() {
    _destination.value = RegisterAccountDestination()
  }

  fun onCompleteNavigation() {
    _destination.value = null
  }
}
