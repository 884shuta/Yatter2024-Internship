package com.dmm.bootcamp.yatter2024.ui.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Media
import com.dmm.bootcamp.yatter2024.ui.bindingmodel.MediaBindingModel

object MediaConverter {
  fun convertToBindingModel(mediaList: List<Media>): List<MediaBindingModel> =
    mediaList.map { convertToBindingModel(it) }

  private fun convertToBindingModel(media: Media): MediaBindingModel = MediaBindingModel(
    id = media.id.value,
    type = media.type,
    url = media.url,
    description = media.description,
  )
}
