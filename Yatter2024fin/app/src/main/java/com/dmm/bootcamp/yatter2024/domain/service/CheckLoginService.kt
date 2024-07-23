package com.dmm.bootcamp.yatter2024.domain.service

interface CheckLoginService {
  suspend fun execute(): Boolean
}
