package com.dmm.bootcamp.yatter2024.common.error

import kotlinx.coroutines.flow.StateFlow

interface ErrorHandler {
  val errorMessage: StateFlow<String?>

  fun handle(errorMessage: String)
}