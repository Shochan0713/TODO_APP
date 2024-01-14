package com.example.mytodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.mytodoapp.adapter.TodoAdapter
import com.example.mytodoapp.model.TodoItem

class MainActivity : AppCompatActivity() {
    private  lateinit var todoAdapter:TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoList = mutableListOf(
            TodoItem("Task 1", false),
            TodoItem("Task 2", true),
        )

        todoAdapter = TodoAdapter(this, todoList)

        val todoListView: ListView = findViewById(R.id.todoListView)
        todoListView.adapter = todoAdapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener{
            addNewTask()
        }
    }
    private  fun addNewTask(){
        val newTask = TodoItem("New Task", false)
        todoAdapter.addTodoItem(newTask)
    }
}
