package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.lifecycle.ViewModel
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class PublicTimelineViewModel(
    private val statusRepository: StatusRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<PublicTimelineUiState> = MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()
}