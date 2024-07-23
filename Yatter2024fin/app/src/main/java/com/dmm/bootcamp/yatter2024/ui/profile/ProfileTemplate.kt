package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.AccountBindingModel
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.component.FullScreenLoadingIndicator
import com.dmm.bootcamp.yatter2024.ui.component.StatusRow
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
fun ProfileTemplate(
  accountBindingModel: AccountBindingModel?,
  statusList: List<StatusBindingModel>,
  isLoading: Boolean,
  onClickNavIcon: () -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(text = "プロフィール")
        },
        navigationIcon = {
          IconButton(onClick = onClickNavIcon) {
            Icon(
              imageVector = Icons.Default.ArrowBack,
              contentDescription = "戻る"
            )
          }
        }
      )
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
    ) {
      if (accountBindingModel != null) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxSize()
        ) {
          Box(contentAlignment = Alignment.Center) {
            AsyncImage(
              modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
              model = accountBindingModel.header,
              contentDescription = "ヘッダー画像"
            )

            AsyncImage(
              modifier = Modifier
                .size(48.dp)
                .padding(top = 102.dp),
              model = accountBindingModel.avatar,
              contentDescription = "アバター画像"
            )
          }

          accountBindingModel.displayName?.let { displayName -> Text(text = displayName) }
          CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
              text = accountBindingModel.username
            )
          }

          accountBindingModel.note?.let { note -> Text(text = note) }

          Row {
            Text(text = accountBindingModel.followingCount.toString())
            Text(text = "フォロー")

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = accountBindingModel.followerCount.toString())
            Text(text = "フォロワー")
          }

          Divider(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 8.dp)
          )

          Box(
            modifier = Modifier.fillMaxSize()
          ) {
            LazyColumn {
              items(statusList) { status ->
                StatusRow(statusBindingModel = status)
              }
            }
          }
        }
      }

      if (isLoading) {
        FullScreenLoadingIndicator()
      }
    }
  }
}

@Preview
@Composable
fun ProfileTemplatePreview() {
  Yatter2024Theme {
    Surface {
      ProfileTemplate(
        accountBindingModel = AccountBindingModel(
          username = "username",
          displayName = "null",
          note = "note",
          avatar = null,
          header = null,
          followingCount = 10,
          followerCount = 10,
        ),
        statusList = listOf(
          StatusBindingModel(
            id = "",
            displayName = "display",
            username = "username",
            avatar = null,
            content = "hogeeeeeee",
            attachmentMediaList = emptyList()
          )
        ),
        isLoading = false,
        onClickNavIcon = {}
      )
    }
  }
}
