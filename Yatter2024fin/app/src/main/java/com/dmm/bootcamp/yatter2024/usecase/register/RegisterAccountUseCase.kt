package com.dmm.bootcamp.yatter2024.usecase.register

interface RegisterAccountUseCase {
  suspend fun execute(
    username: String,
    password: String
  ): RegisterAccountUseCaseResult
}
