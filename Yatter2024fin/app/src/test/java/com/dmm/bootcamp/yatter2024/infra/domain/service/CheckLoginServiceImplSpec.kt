package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CheckLoginServiceImplSpec {
  private val tokenPreferences = mockk<TokenPreferences>()
  private val subject = CheckLoginServiceImpl(tokenPreferences)

  @Test
  fun getTrueWhenSavedUsername() = runTest {
    val accessToken = "accessToken"

    coEvery {
      tokenPreferences.getAccessToken()
    } returns accessToken

    val result: Boolean = subject.execute()

    assertThat(result).isTrue()

    verify {
      tokenPreferences.getAccessToken()
    }
  }

  @Test
  fun getFalseWhenUnsavedUsername() = runTest {
    val accessToken = ""

    coEvery {
      tokenPreferences.getAccessToken()
    } returns accessToken

    val result: Boolean = subject.execute()

    assertThat(result).isFalse()

    verify {
      tokenPreferences.getAccessToken()
    }
  }
}