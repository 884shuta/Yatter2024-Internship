package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.LoginRequestBodyJson
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences

class LoginServiceImpl(
  private val yatterApi: YatterApi,
  private val tokenPreferences: TokenPreferences,
) : LoginService {

  override suspend fun execute(
    username: Username,
    password: Password,
  ) {
    val requestJson = LoginRequestBodyJson(
      username.value,
      password.value,
    )
    val response = yatterApi.login(requestJson)

    tokenPreferences.putAccessToken(response.accessToken)
  }
}
