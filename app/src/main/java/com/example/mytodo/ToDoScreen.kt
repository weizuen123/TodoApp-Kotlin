package com.example.mytodo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun ToDoScreen() {
    val viewModel: TodoViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = { AddToDo({ viewModel.onClickForAddButton() }) }) { innerPadding ->

            if(viewModel.showDialog){
                AddToDoDialog(
                    innerPadding,
                    onDismissReq = {viewModel.onDismissDialog()},
                    confirmBut = {viewModel.onConfirmButton()},
                    dismissBut = {viewModel.onDismissDialog()},
                    textUserTyped = viewModel.textUserTyped,
                    onTextChange = {
                        viewModel.onTextChange(it)
                    }
                    )
            }

            Column(modifier = Modifier.padding(innerPadding)) {
                MyLazyColumn(viewModel.todoList,
                    { todo, checked ->
                    viewModel.onTodoCheckChange(todo,checked)
                })
            }
    }

}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun MyLazyColumn(todos: List<Todo>, onCheckedChange2: (Todo,Boolean) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(todos, key = { it.id.toString() }){
            todo ->
            TodoItem(todo, {checked -> onCheckedChange2(todo, checked)})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(todo: Todo, onCheckedChange: (Boolean) -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isDone)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (todo.isDone) 0.dp else 3.dp
        ),
        onClick = { onCheckedChange(!todo.isDone) }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = onCheckedChange
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = todo.text,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None,
                color = if (todo.isDone)
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.onSurface
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
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add todo"
        )
    }
}

