package com.example.mytodo

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Todo @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.random(),
    val text: String,
    val isDone: Boolean = false
)

