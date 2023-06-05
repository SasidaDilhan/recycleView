package com.example.recycleview.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recycleview.database.entities.Todo

@Dao
interface ToDoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todos")
    fun getAllTodos():List<Todo>
}