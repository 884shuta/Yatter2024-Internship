package com.dmm.bootcamp.yatter2024.usecase.post

import java.io.File

interface PostStatusUseCase {
  suspend fun execute(
    content: String,
    attachmentList: List<File>
  ): PostStatusUseCaseResult
}
