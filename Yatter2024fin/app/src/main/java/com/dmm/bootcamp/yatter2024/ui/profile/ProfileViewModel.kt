package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter.AccountBindingModelConverter
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
  private val username: String?,
  private val accountRepository: AccountRepository,
  private val getMeService: GetMeService,
  private val statusRepository: StatusRepository,
) : ViewModel() {
  private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.empty())
  val uiState: StateFlow<ProfileUiState> = _uiState

  private val _destination = MutableStateFlow<Destination?>(null)
  val destination: StateFlow<Destination?> = _destination.asStateFlow()

  init {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }
      val account = if (username == null) {
        getMeService.execute()
      } else {
        accountRepository.findByUsername(Username(username))
      }

      if (account == null) {
        // エラーハンドリング
      } else {
        _uiState.update {
          it.copy(
            accountBindingModel = AccountBindingModelConverter.convertToBindingModel(
              account
            ),
            mine = account is Me
          )
        }
      }

      _uiState.update {
        it.copy(statusList = StatusConverter.convertToBindingModel(statusRepository.findAllHome()))
      }

      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickFollowing() {
  }

  fun onClickFollower() {
  }

  fun onClickFollow() {
  }

  fun onClickUnFollow() {
  }

  fun onClickEditProfile() {
  }

  fun onClickNavIcon() {
    _destination.value = PopBackDestination
  }

  fun onCompleteNavigation() {
    _destination.value = null
  }
}
