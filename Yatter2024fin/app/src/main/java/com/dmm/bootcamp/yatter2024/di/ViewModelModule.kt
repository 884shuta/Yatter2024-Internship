package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.MainViewModel
import com.dmm.bootcamp.yatter2024.ui.login.LoginViewModel
import com.dmm.bootcamp.yatter2024.ui.post.PostViewModel
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileViewModel
import com.dmm.bootcamp.yatter2024.ui.register.RegisterAccountViewModel
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
  viewModel { MainViewModel(get()) }
  viewModel { PublicTimelineViewModel(get()) }
  viewModel { PostViewModel(get(), get()) }
  viewModel { RegisterAccountViewModel(get()) }
  viewModel { (username: String?) -> ProfileViewModel(username = username, get(), get(), get()) }
  viewModel { LoginViewModel(get()) }
}
