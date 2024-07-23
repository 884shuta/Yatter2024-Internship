package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class ProfileDestination(
  private val username: String?
) : Destination(ROUTE) {
  override fun buildRoute(): String {
    return buildString {
      append(ROUTE_PATH)
      if (username != null) {
        append("?$KEY_USERNAME=$username")
      }
    }
  }

  companion object {
    private const val ROUTE_PATH = "profile"
    private const val KEY_USERNAME = "username"
    private const val ROUTE = "$ROUTE_PATH?$KEY_USERNAME={$KEY_USERNAME}"

    fun createNode(builder: NavGraphBuilder) {
      builder.composable(
        route = ROUTE,
        arguments = listOf(
          navArgument(KEY_USERNAME) {
            nullable = true
            type = NavType.StringType
          },
        ),
      ) { backStackEntry ->
        val username = backStackEntry.arguments?.getString(KEY_USERNAME)
        ProfilePage(username)
      }
    }
  }
}
