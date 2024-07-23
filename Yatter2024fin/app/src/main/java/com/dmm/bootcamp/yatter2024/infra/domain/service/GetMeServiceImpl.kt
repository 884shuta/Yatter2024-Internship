package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMeServiceImpl(
  private val accountRepository: AccountRepository,
) : GetMeService {
  override suspend fun execute(): Me? = withContext(Dispatchers.IO) {
    accountRepository.findMe()
  }
}
