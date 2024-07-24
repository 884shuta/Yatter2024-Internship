package com.dmm.bootcamp.yatter2024.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel(){

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

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
            _uiState.update { it.copy(isLoading = true) } // 1
            val snapBindingModel = uiState.value.loginBindingModel
            when (
                val result =
                    loginUseCase.execute(
                        Username(snapBindingModel.username),
                        Password(snapBindingModel.password),
                    ) // 2
            ) {
                is LoginUseCaseResult.Success -> { // パブリックタイムライン画⾯に遷移する処理の追加
                    _destination.value = PublicTimelineDestination()
                }
                is LoginUseCaseResult.Failure -> { // エラー表⽰
                }
            }
            _uiState.update { it.copy(isLoading = true) } // 5
            _destination.value = PublicTimelineDestination {
                popUpTo(LoginDestination().route) {
                    inclusive = true
                }
            }
        }
    }
    fun onClickRegister() {
        // _destination.value = RegisterAccountDestination()
    }
    fun onCompleteNavigation() {
        _destination.value = null
    }

}