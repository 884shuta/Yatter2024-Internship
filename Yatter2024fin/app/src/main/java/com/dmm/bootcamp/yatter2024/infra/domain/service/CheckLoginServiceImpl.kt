package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences

class CheckLoginServiceImpl(
  private val tokenPreferences: TokenPreferences,
) : CheckLoginService {
  override suspend fun execute(): Boolean {
    return tokenPreferences.getAccessToken().isNullOrEmpty().not()
  }
}
