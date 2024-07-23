package com.dmm.bootcamp.yatter2024.ui.component

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
import androidx.compose.material.Surface
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
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
internal fun StatusRow(
  statusBindingModel: StatusBindingModel,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    val context = LocalContext.current
    val placeholder = ResourcesCompat.getDrawable(
      context.resources,
      R.drawable.avatar_placeholder,
      null,
    )
    AsyncImage(
      modifier = Modifier.size(48.dp),
      model = ImageRequest.Builder(context)
        .data(statusBindingModel.avatar)
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .setHeader("User-Agent", "Mozilla/5.0")
        .build(),
      contentDescription = "アバター画像",
      contentScale = ContentScale.Crop,
    )

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = buildAnnotatedString {
          append(statusBindingModel.displayName)
          withStyle(
            style = SpanStyle(
              color = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium),
            )
          ) {
            append(" @${statusBindingModel.username}")
          }
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
      )

      Text(text = statusBindingModel.content)

      LazyRow {
        items(statusBindingModel.attachmentMediaList) { attachmentMedia ->
          AsyncImage(
            model = attachmentMedia.url,
            contentDescription = attachmentMedia.description
          )
          Spacer(modifier = Modifier.width(4.dp))
        }
      }
    }
  }
}

@Preview
@Composable
private fun StatusRowPreview() {
  Yatter2024Theme {
    Surface {
      StatusRow(
        statusBindingModel = StatusBindingModel(
          id = "id",
          displayName = "mito",
          username = "mitohato14",
          avatar = "https://avatars.githubusercontent.com/u/19385268?v=4",
          content = "preview content",
          attachmentMediaList = emptyList()
        )
      )
    }
  }
}
