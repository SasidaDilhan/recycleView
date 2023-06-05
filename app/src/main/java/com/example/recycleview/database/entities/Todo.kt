package com.example.recycleview.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    var item:String?
){
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}
