package com.example.mytodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.set
import kotlin.uuid.ExperimentalUuidApi

class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel(){
    val todoList: StateFlow<List<Todo>> = repository.todos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )
    var showDialog by mutableStateOf(false)
        private set
    var textUserTyped by mutableStateOf("")
        private set
    fun onClickForAddButton(){
        showDialog = true
    }

    fun onConfirmButton(){
        viewModelScope.launch {
            repository.addTodo(textUserTyped)
        }
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

    fun onTodoCheckChange(todo: Todo, checked: Boolean){
        viewModelScope.launch {
            repository.setDone(todo, checked)
        }
    }

    fun onTodoDelete(todo: Todo){
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

}



