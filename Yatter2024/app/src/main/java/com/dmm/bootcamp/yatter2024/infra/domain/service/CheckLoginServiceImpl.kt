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

//class CheckLoginServiceImplSpec {
//  private val tokenPreferences = mockk<TokenPreferences>()
//  private val subject = CheckLoginServiceImpl(tokenPreferences)
//  @Test
//  fun getTrueWhenSavedUsername() = runTest {
//    val accessToken = "accessToken"
//    coEvery {
//      tokenPreferences.getAccessToken()
//    } returns accessToken
//    val result: Boolean = subject.execute()
//    assertThat(result).isTrue()
//    verify {
//      tokenPreferences.getAccessToken()
//    }
//  }
//  @Test
//  fun getFalseWhenUnsavedUsername() = runTest {
//    val accessToken = ""
//    coEvery {
//      tokenPreferences.getAccessToken()
//    } returns accessToken
//    val result: Boolean = subject.execute()
//    assertThat(result).isFalse()
//    verify {
//      tokenPreferences.getAccessToken()
//    }
//  }
//}
