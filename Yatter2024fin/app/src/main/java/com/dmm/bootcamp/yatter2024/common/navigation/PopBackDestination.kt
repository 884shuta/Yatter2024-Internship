package com.dmm.bootcamp.yatter2024.common.navigation

import androidx.navigation.NavController

object PopBackDestination : Destination("popback") {
  override fun navigate(navController: NavController) {
    navController.popBackStack()
  }
}
