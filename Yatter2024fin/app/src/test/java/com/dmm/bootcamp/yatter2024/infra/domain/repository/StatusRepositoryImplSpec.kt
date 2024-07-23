package com.dmm.bootcamp.yatter2024.infra.domain.repository

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.AccountJson
import com.dmm.bootcamp.yatter2024.infra.api.json.PostStatusJson
import com.dmm.bootcamp.yatter2024.infra.api.json.StatusJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.infra.domain.model.AccountImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.URL

class StatusRepositoryImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val tokenProvider = mockk<TokenProvider>()
  private val subject = StatusRepositoryImpl(yatterApi, tokenProvider)

  @Test
  fun getPublicTimelineFromApi() = runTest {
    val jsonList = listOf(
      StatusJson(
        id = "id",
        account = AccountJson(
          id = "id",
          username = "username",
          displayName = "display name",
          note = "note",
          avatar = "https://www.google.com",
          header = "https://www.google.com",
          followingCount = 100,
          followersCount = 200,
          createdAt = "2023-06-02T12:44:35.030Z"
        ),
        content = "content",
        createdAt = "2023-06-02T12:44:35.030Z",
        attachmentMediaList = emptyList(),
      )
    )

    val expect = listOf(
      Status(
        id = StatusId(value = "id"),
        account = AccountImpl(
          id = AccountId("id"),
          username = Username("username"),
          displayName = "display name",
          note = "note",
          avatar = URL("https://www.google.com"),
          header = URL("https://www.google.com"),
          followingCount = 100,
          followerCount = 200
        ),
        content = "content",
        attachmentMediaList = emptyList()
      )
    )

    coEvery {
      yatterApi.getPublicTimeline()
    } returns jsonList

    val result: List<Status> = subject.findAllPublic()

    coVerify {
      yatterApi.getPublicTimeline()
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun postStatusWhenLoggedIn() = runTest {
    val token = "token"
    val content = "content"

    val statusJson = StatusJson(
      id = "id",
      account = AccountJson(
        id = "id",
        username = token,
        displayName = "",
        note = null,
        avatar = "https://www.google.com",
        header = "https://www.google.com",
        followingCount = 0,
        followersCount = 0,
        createdAt = ""
      ),
      content = content,
      createdAt = "",
      attachmentMediaList = emptyList(),
    )

    coEvery {
      tokenProvider.provide()
    } returns token

    coEvery {
      yatterApi.postStatus(any(), any())
    } returns statusJson

    val expect = StatusConverter.convertToDomainModel(statusJson)

    val result = subject.create(
      content,
      emptyList()
    )

    assertThat(result).isEqualTo(expect)

    coVerifyAll {
      tokenProvider.provide()
      yatterApi.postStatus(
        token,
        PostStatusJson(
          status = content,
          mediaList = emptyList()
        )
      )
    }
  }

  @Test
  fun postStatusWhenNotLoggedIn() = runTest {
    val content = "content"

    coEvery {
      tokenProvider.provide()
    } throws AuthenticatorException()


    var error: Throwable? = null
    var result: Status? = null

    try {
      result = subject.create(
        content,
        emptyList()
      )
    } catch (e: Exception) {
      error = e
    }


    assertThat(result).isNull()
    assertThat(error).isInstanceOf(AuthenticatorException::class.java)

    coVerify {
      tokenProvider.provide()
    }
  }
}