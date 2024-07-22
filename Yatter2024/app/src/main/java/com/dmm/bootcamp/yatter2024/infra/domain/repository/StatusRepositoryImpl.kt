package com.dmm.bootcamp.yatter2024.infra.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.Status
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.PostStatusJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.StatusConverter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.File

class StatusRepositoryImpl(
  private val yatterApi: YatterApi,
  private val tokenProvider: TokenProvider,
) : StatusRepository {
  override suspend fun findById(id: StatusId): Status? {
    TODO("Not yet implemented")
  }

  override suspend fun findAllPublic(): List<Status> = withContext(IO) {
    val statusList = yatterApi.getPublicTimeline()
    StatusConverter.convertToDomainModel(statusList)
  }

  override suspend fun findAllHome(): List<Status> = withContext(IO) {
    val statusList = yatterApi.getHomeTimeline(tokenProvider.provide())
    StatusConverter.convertToDomainModel(statusList)
  }

  override suspend fun create(
    content: String,
    attachmentList: List<File>
  ): Status = withContext(IO) {
    val statusJson = yatterApi.postStatus(
      tokenProvider.provide(),
      PostStatusJson(
        content,
        listOf()
      )
    )
    StatusConverter.convertToDomainModel(statusJson)
  }
  override suspend fun delete(status: Status) {
    TODO("Not yet implemented")
  }
}
