package com.example.recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.adapter.TodoAdapter
import com.example.recycleview.database.TodoDatabase
import com.example.recycleview.database.entities.Todo
import com.example.recycleview.database.reporsitory.Todoreporsitory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ui = this
        val reporsitory = Todoreporsitory(TodoDatabase.getInstance(this))
        val recycleView:RecyclerView = findViewById(R.id.rvTodoList)
        val adapter = TodoAdapter()

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(ui)
        val btnAddTodo = findViewById<Button>(R.id.btnAddTodo)

        btnAddTodo.setOnClickListener {
            displayDialog(reporsitory,adapter)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = reporsitory.getall()
            adapter.setData(data,ui)
        }
    }
    fun displayDialog(repository:Todoreporsitory, adapter: TodoAdapter){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Enter New Todo item :")
        builder.setMessage("Enter the new todo item below")


        //getting user inputs
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK"){
            dialog, which ->
            val item = input.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Todo(item))
                val data = repository.getall()
                runOnUiThread{
                    adapter.setData(data,this@MainActivity)
                }
            }
        }

        builder.setNegativeButton("cancel"){
            dialog,which ->

            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}