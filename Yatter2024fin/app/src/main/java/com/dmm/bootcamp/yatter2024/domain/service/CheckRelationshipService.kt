package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.domain.model.Relationship
import com.dmm.bootcamp.yatter2024.domain.model.Username

interface CheckRelationshipService {
  suspend fun execute(
    me: Me,
    usernameList: List<Username>
  ): List<Relationship>
}
