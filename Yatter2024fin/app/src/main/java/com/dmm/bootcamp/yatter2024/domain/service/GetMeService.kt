package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Me

interface GetMeService {
  suspend fun execute(): Me?
}
