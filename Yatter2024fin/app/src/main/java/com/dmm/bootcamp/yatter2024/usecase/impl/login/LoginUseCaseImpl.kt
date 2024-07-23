package com.dmm.bootcamp.yatter2024.usecase.impl.login

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult

internal class LoginUseCaseImpl(
  private val loginService: LoginService,
  private val mePreferences: MePreferences,
) : LoginUseCase {
  override suspend fun execute(
    username: Username,
    password: Password
  ): LoginUseCaseResult {
    try {
      if (username.value.isBlank()) return LoginUseCaseResult.Failure.EmptyUsername
      if (password.value.isBlank()) return LoginUseCaseResult.Failure.EmptyPassword

      if (!password.validate()) return LoginUseCaseResult.Failure.InvalidPassword
      loginService.execute(username, password)
      mePreferences.putUserName(username.value)
      return LoginUseCaseResult.Success
    } catch (e: Exception) {
      return LoginUseCaseResult.Failure.OtherError(e)
    }
  }
}