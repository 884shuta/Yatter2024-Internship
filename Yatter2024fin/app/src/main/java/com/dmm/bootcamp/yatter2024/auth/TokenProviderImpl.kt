package com.dmm.bootcamp.yatter2024.auth

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences

class TokenProviderImpl(private val tokenPreferences: TokenPreferences) : TokenProvider {
  override suspend fun provide(): String {
    return tokenPreferences.getAccessToken()?.let { "username $it" } ?: throw AuthenticatorException()
  }
}