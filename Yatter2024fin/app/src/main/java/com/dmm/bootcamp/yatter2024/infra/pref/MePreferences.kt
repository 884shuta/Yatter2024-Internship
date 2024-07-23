package com.dmm.bootcamp.yatter2024.infra.pref

import android.content.Context
import androidx.core.content.edit

class MePreferences(context: Context) {
  companion object {
    private const val PREF_NAME = "me"
    private const val KEY_USERNAME = "username"
  }

  private val sharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE
  )

  fun getUsername(): String? = sharedPreferences.getString(
    KEY_USERNAME,
    ""
  )

  fun putUserName(username: String?) {
    sharedPreferences.edit {
      putString(
        KEY_USERNAME,
        username
      )
    }
  }

  fun clear() = sharedPreferences.edit {
    clear()
  }
}
