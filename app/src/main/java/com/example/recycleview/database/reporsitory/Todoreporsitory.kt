package com.example.recycleview.database.reporsitory

import com.example.recycleview.database.TodoDatabase
import com.example.recycleview.database.entities.Todo

class Todoreporsitory(
    private val db :TodoDatabase
) {
    suspend fun insert(todo: Todo) = db.ToDoDao().insert(todo)
    suspend fun delete(todo: Todo) = db.ToDoDao().delete(todo)
    fun getall() = db.ToDoDao().getAllTodos()
}