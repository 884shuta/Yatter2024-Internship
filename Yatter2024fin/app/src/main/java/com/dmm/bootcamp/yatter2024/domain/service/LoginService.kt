package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username

interface LoginService {
  suspend fun execute(
    username: Username,
    password: Password,
  )
}