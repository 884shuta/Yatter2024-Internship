package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.domain.repository.AccountRepositoryImpl
import com.dmm.bootcamp.yatter2024.infra.domain.repository.StatusRepositoryImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.CheckLoginServiceImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.GetMeServiceImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.LoginServiceImpl
import org.koin.dsl.module

internal val domainImplModule = module {
  single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
  single<StatusRepository> { StatusRepositoryImpl(get(), get()) }

  factory<GetMeService> { GetMeServiceImpl(get()) }
  factory<LoginService> { LoginServiceImpl(get(), get()) }
  factory<CheckLoginService> { CheckLoginServiceImpl(get()) }
}
