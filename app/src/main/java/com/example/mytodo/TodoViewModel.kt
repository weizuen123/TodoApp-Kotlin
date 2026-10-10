package com.example.mytodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.text.set
import kotlin.uuid.ExperimentalUuidApi

class TodoViewModel : ViewModel(){
    private val repository = TodoRepository()
    val todoList: List<Todo> get() = repository.todoList
    var showDialog by mutableStateOf(false)
        private set
    var textUserTyped by mutableStateOf("")
        private set
    fun onClickForAddButton(){
        showDialog = true
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onConfirmButton(){
        repository.addTodo(textUserTyped)
        showDialog = false
        textUserTyped = ""
    }

    fun onDismissDialog(){
        showDialog = false
        textUserTyped = ""
    }

    fun onTextChange(it: String){
        textUserTyped = it
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onTodoCheckChange(todo: Todo, checked: Boolean){
        repository.setDone(todo,checked)
    }

}



