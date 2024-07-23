package com.dmm.bootcamp.yatter2024.auth

interface TokenProvider {
  suspend fun provide(): String
}