package com.dmm.bootcamp.yatter2024.ui.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class LoginDestination : Destination(ROUTE) {
    companion object {
        private const val ROUTE = "login"
        fun createNode(builder: NavGraphBuilder) {
            7 / 13
            builder.composable(ROUTE) {
                LoginPage()
            }
        }
    }
}