package com.example.mytodo

import androidx.compose.runtime.mutableStateListOf
import kotlin.uuid.ExperimentalUuidApi

class TodoRepository {
    private val _todoList = mutableStateListOf<Todo>()
    val todoList: List<Todo> get() = _todoList

    @OptIn(ExperimentalUuidApi::class)
    fun addTodo(textUserTyped: String){
        _todoList.add(Todo(text = textUserTyped))
    }

    @OptIn(ExperimentalUuidApi::class)
    fun setDone(todo: Todo, checked: Boolean){
        val index = _todoList.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            _todoList[index] = todo.copy(isDone = checked)
        }
    }

}