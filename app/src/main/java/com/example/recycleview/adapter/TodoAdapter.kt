package com.example.recycleview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recycleview.R
import com.example.recycleview.database.TodoDatabase
import com.example.recycleview.database.entities.Todo
import com.example.recycleview.database.reporsitory.Todoreporsitory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    lateinit var data :List<Todo>
    lateinit var context:Context

    class ViewHolder(view : View):RecyclerView.ViewHolder(view){

        val cbTodo:CheckBox
        val ivDelet:ImageView

        init {
            cbTodo = view.findViewById(R.id.cbTodo)
            ivDelet = view.findViewById(R.id.ivDelete)
        }
    }
    fun setData(data:List<Todo>,context: Context){
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cbTodo.text = data[position].item

        holder.ivDelet.setOnClickListener {

            if (holder.cbTodo.isChecked){
                val repository = Todoreporsitory(TodoDatabase.getInstance(context))

                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(data[position])
                    val data = repository.getall()

                    withContext(Dispatchers.Main){
                        setData(data,context)
                    }
                }
            }else{
                Toast.makeText(context, "Cannot delete unchecked todos",Toast.LENGTH_LONG).show()
            }
        }
    }
}