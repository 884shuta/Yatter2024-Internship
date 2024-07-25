package com.dmm.bootcamp.yatter2024.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import com.dmm.bootcamp.yatter2024.ui.post.PostTemplate
import com.dmm.bootcamp.yatter2024.ui.post.PostViewModel
import org.koin.androidx.compose.getViewModel

//@Composable
//fun SplashPage(
//    viewModel: SplashViewModel = getViewModel(),
//){
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val destination by viewModel.destination.collectAsStateWithLifecycle()
//    val navController = LocalNavController.current
//    LaunchedEffect (destination){
//        destination?.navigate(navController)
//        viewModel.onCompleteNavigation()
//    }
//    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
//        viewModel.onCreate()
//    }
//    SplashTemplate(
//        splashBindingModel = uiState.bindingModel,
//        isLoading = uiState.isLoading,
//        //canPost = uiState.canPost,
//        //onStatusTextChanged = viewModel::onChangedStatusText,
//        //onClickPost = viewModel::onClickPost,
//        onClickNavIcon = viewModel::onClickNavIcon,
//    )
//
//    }
//}