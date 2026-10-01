package com.example.mytodo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ToDoScreen() {

    var showDialog by remember { mutableStateOf(false) }
    var textUserTyped by remember {mutableStateOf("")}
    val todoList = remember { mutableStateListOf<Todo>() }

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = { AddToDo( {showDialog = true}) }) { innerPadding ->

            if(showDialog == true){
                AddToDoDialog(
                    innerPadding,
                    onDismissReq = {showDialog = false
                        textUserTyped = ""},
                    confirmBut = {todoList.add(Todo(text = textUserTyped))
                                 showDialog = false
                                 textUserTyped = ""},
                    dismissBut = {showDialog = false
                        textUserTyped = ""},
                    textUserTyped = textUserTyped,
                    onTextChange = {
                        textUserTyped = it
                    }
                    )
            }

            Column(modifier = Modifier.padding(innerPadding)) {
                MyLazyColumn(todoList, { todo, checked ->
                    val index = todoList.indexOfFirst { it.id == todo.id }
                    todoList[index] = todo.copy(isDone = checked)
                })
            }
    }

}

@Composable
fun MyLazyColumn(todos: List<Todo>, onCheckedChange2: (Todo,Boolean) -> Unit){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(todos){
            todo ->
            TodoItem(todo, {checked -> onCheckedChange2(todo, checked)})
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onCheckedChange: (Boolean) -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(),
        shape = CardDefaults.shape
    ){
        Row() {
            Text(todo.text)
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = onCheckedChange
            )
        }
    }
}



@Composable
fun AddToDoDialog(
    innerPadding: PaddingValues,
    onDismissReq: () -> Unit,
    confirmBut: () -> Unit,
    dismissBut: () -> Unit,
    textUserTyped: String,
    onTextChange: (String) -> Unit)
{
    AlertDialog(
        modifier = Modifier.padding(innerPadding),
        onDismissRequest = onDismissReq,
        confirmButton = {
            TextButton(onClick = confirmBut) {
                Text(text = "save")
            }
        },
        dismissButton = {
            TextButton(onClick = dismissBut) {
                Text(text = "cancel")
            }
        },
        title = {Text("Add todo list")},
        text = {
            OutlinedTextField(
                value = textUserTyped,
                onValueChange =  onTextChange
            )
        }

    )
}

@Composable
fun AddToDo(onClick: () -> Unit){
    FloatingActionButton(
        onClick = onClick
    ) { }
}

