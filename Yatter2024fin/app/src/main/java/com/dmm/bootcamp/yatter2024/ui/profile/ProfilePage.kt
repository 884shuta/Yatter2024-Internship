package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfilePage(
  username: String?,
  viewModel: ProfileViewModel = getViewModel {
    parametersOf(username)
  },
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val destination by viewModel.destination.collectAsStateWithLifecycle()
  val navController = LocalNavController.current
  LaunchedEffect(destination) {
    destination?.navigate(navController)
    viewModel.onCompleteNavigation()
  }
  ProfileTemplate(
    accountBindingModel = uiState.accountBindingModel,
    statusList = uiState.statusList,
    isLoading = uiState.isLoading,
    onClickNavIcon = viewModel::onClickNavIcon,
  )
}
