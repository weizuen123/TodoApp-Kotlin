package com.example.mytodo

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi

class TodoRepository(private val todoDao: TodoDao) {
    val todos: Flow<List<Todo>> = todoDao.getAll()

    suspend fun addTodo(textUserTyped: String){
        todoDao.insert(Todo(text = textUserTyped))
    }

    suspend fun setDone(todo: Todo, isDone: Boolean){
        todoDao.update(todo.copy(isDone = isDone))
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.delete(todo)
    }

}