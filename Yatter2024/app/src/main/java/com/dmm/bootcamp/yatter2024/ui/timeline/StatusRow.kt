package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.material.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.MediaBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

@Composable
fun StatusRow(
    statusBindingModel: StatusBindingModel,
    modifier: Modifier = Modifier.padding(24.dp),
) {
    Row(//Rowコンポーザブルは{}内のコンテンツを左から右に横一列にまとめる。
        modifier = modifier
            .fillMaxWidth()//このRowは横幅を取れるだけとる。
            .padding(vertical = 4.dp),//縦⽅向(vertical)に4dpのpaddingを当てる
        horizontalArrangement = Arrangement.spacedBy(8.dp),//横⼀列に並べるコンポ ーザブル同⼠の隙間を8dp空ける
    ){
        val context = LocalContext.current

        val placeholder = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.avatar_placeholder,
            null,
        )
        AsyncImage(//URLから画像データを読み込み、自動的に表示する
            modifier = Modifier.size(48.dp),//アイコンサイズ
            model = ImageRequest.Builder(context)//画像URLや画像
                .data(statusBindingModel.avatar)
                .placeholder(placeholder)
                .error(placeholder)
                .fallback(placeholder)
                .setHeader("User-Agent", "Mozilla/5.0")
                .build(),
            contentDescription = stringResource(id = R.string.public_timeline_avatar_content_description),//画像の説明テキスト
            contentScale = ContentScale.Crop//コンポーザブルのサイズにCrop
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)){//内容を縦⽅向に並べる。縦⽅向になるため、 verticalArrangementに余⽩を指定
            Text(//Rowを使わず一つにまとめることで長い場合に...で省略できるようにする。
                text = buildAnnotatedString {//一つのコンポーザブルで見た目の違う文字列を表現するために使用
                    append(statusBindingModel.displayName)//appendで文字列をセット
                    withStyle(//見た目を変えたい文字列部分に使用
                        style = SpanStyle(
                            color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
                            //文字色を薄くするためにContentAlpha.mediumを指定
                        )
                    ){
                        append(" @${statusBindingModel.username}")
                    }
                },
                maxLines = 1,//文字列が複数行にならないように
                overflow = TextOverflow.Ellipsis,//はみ出した部分を...で表現
                fontWeight = FontWeight.Bold,//文字を太字に
            )
            Text(text = statusBindingModel.content)
            LazyRow {
                //itemsの第一引数に並べたいデータセットを渡す
                items(statusBindingModel.attachmentMediaList){ attachmentMedia ->
                    //データ1件あたりに表示したいコンポーザブルを呼び出す
                    AsyncImage(
                        model = attachmentMedia.url,
                        contentDescription = attachmentMedia.description,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

//以下でプレビューが利用可能に
@Preview
/*このアノテーションをつけたコンポーザブル内で
プレビューを表示したいコンポーザブルを呼び出すことで表示できる。*/
@Composable
private fun StatusRowPreview() {
    Yatter2024Theme {
        Surface {
            StatusRow(
                statusBindingModel = StatusBindingModel(//StatusRowの引数。プレビュー用なので中身は適当な値でいい
                    id = "id",
                    displayName = "Shuta",
                    username = "884shuta",
                    avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
                    content = "preview content hogehoge",
                    attachmentMediaList = listOf(
                        MediaBindingModel(
                            id = "id",
                            type = "image",
                            url = "https://avatars.githubusercontent.com/u/39693306? v=4", description = "icon"
                        )
                    )
                )
            )
        }
    }
}