package com.dmm.bootcamp.yatter2024.ui.timeline



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PublicTimelineTemplate(
    statusList: List<StatusBindingModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    onClickPost: () -> Unit,
    onRefresh: () -> Unit,

){
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)
    if(isRefreshing){}else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "タイムライン")
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onClickPost) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "post"
                    )
                }
            },
        ) { paddingValues ->
            //Scaffoldコンポーザブルとは、TopAppBar、BottomAppBar、FloatingActionButton、Drawerなどの ⼀般的なマテリアルデザインのレイアウトを提供します
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)//これ使ったら、paddingValuesが使用される。
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center,
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),//画像全体にStatus一覧を表示
                    contentPadding = PaddingValues(8.dp)
                ) {//Scrollが必要ならLazyのついたコンポーザブルを利用する(画面の範囲のみ読み込まれるため<->foreachを使うと全てが読み込まれ重くなる)
                    items(statusList) { item ->
                        StatusRow(statusBindingModel = item)
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
private fun PublicTimelineTemplatePreview() {
    Yatter2024Theme {
        Surface {
            PublicTimelineTemplate(
                statusList = listOf(
                    StatusBindingModel(
                        id = "id1",
                        displayName = "display name1",
                        username = "username1",
                        avatar = null,
                        content = "preview content1",
                        attachmentMediaList = listOf()
                    ),
                    StatusBindingModel(
                        id = "id2",
                        displayName = "display name2",
                        username = "username2",
                        avatar = null,
                        content = "preview content2",
                        attachmentMediaList = listOf()
                    ),
                ),
                isLoading = true,
                isRefreshing = false,
                onRefresh = {},
                onClickPost = {},
            )
        }
    }
}
