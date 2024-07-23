package com.dmm.bootcamp.yatter2024.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

abstract class Destination(
  val route: String,
  val builder: (NavOptionsBuilder.() -> Unit)? = null,
) {
  open fun buildRoute(): String = route
  open fun navigate(navController: NavController) {
    navController.navigate(buildRoute(), builder?.let { navOptions(it) })
  }
}
