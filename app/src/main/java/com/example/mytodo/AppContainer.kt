package com.example.mytodo

import android.content.Context

class AppContainer(context: Context) {
    private val database = TodoDatabase.getDatabase(context)
    val todoRepository = TodoRepository(database.todoDao())
}