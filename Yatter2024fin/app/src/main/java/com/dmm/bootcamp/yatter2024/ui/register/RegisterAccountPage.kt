package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterAccountPage(
  viewModel: RegisterAccountViewModel = getViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val destination by viewModel.destination.collectAsStateWithLifecycle()
  val navController = LocalNavController.current
  LaunchedEffect(destination) {
    destination?.let {
      it.navigate(navController)
      viewModel.onCompleteNavigation()
    }
  }
  RegisterAccountTemplate(
    userName = uiState.bindingModel.userName,
    onChangedUserName = viewModel::onChangedUserName,
    password = uiState.bindingModel.password,
    onChangedPassword = viewModel::onChangedPassword,
    isLoading = uiState.isLoading,
    onClickRegister = viewModel::onClickRegister,
    onClickLogin = viewModel::onClickLogin,
  )
}
