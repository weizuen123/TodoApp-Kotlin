package com.example.mytodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel(){
    var showDialog by mutableStateOf(false)
        private set
    fun onClickForAddButton(){
        showDialog = true
    }
}



