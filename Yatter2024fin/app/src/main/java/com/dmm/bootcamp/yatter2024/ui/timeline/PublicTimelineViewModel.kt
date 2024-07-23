package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PublicTimelineViewModel(
  private val statusRepository: StatusRepository,
) : ViewModel() {
  private val _uiState: MutableStateFlow<PublicTimelineUiState> =
    MutableStateFlow(PublicTimelineUiState.empty())
  val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

  private val _destination = MutableStateFlow<Destination?>(null)
  val destination: StateFlow<Destination?> = _destination.asStateFlow()

  fun onResume() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }
      fetchPublicTimeline()
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickPost() {
    _destination.value = PostDestination()
  }

  fun onRefresh() {
    viewModelScope.launch {
      _uiState.update { it.copy(isRefreshing = true) }
      fetchPublicTimeline()
      _uiState.update { it.copy(isRefreshing = false) }
    }
  }

  fun onClickProfile() {
    _destination.value = ProfileDestination(null)
  }

  private suspend fun fetchPublicTimeline() {
    val statusList = statusRepository.findAllPublic()
    _uiState.update {
      it.copy(
        statusList = StatusConverter.convertToBindingModel(statusList),
      )
    }
  }

  fun onCompleteNavigation() {
    _destination.value = null
  }
}
