package com.dmm.bootcamp.yatter2024.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginTest() {
  Column(modifier = Modifier.fillMaxSize()) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp),
      text = "ユーザー名"
    )
    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      value = "",
      onValueChange = {},
      placeholder = {
        Text(text = "username")
      },
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "パスワード"
    )
    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      value = "",
      onValueChange = {},
      placeholder = {
        Text(text = "password")
      },
    )
  }}

@Preview(showBackground = true)
@Composable
fun LoginTestPreview() {
  LoginTest()
}
