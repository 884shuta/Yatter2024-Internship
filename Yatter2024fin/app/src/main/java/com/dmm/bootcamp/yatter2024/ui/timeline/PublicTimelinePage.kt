package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
internal fun PublicTimelinePage(
  viewModel: PublicTimelineViewModel = getViewModel(),
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
  LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
    viewModel.onResume()
  }

  PublicTimelineTemplate(
    statusList = uiState.statusList,
    isLoading = uiState.isLoading,
    onClickPost = viewModel::onClickPost,
    isRefreshing = uiState.isRefreshing,
    onRefresh = viewModel::onRefresh,
    onClickProfile = viewModel::onClickProfile,
  )
}
