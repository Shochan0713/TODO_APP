package com.example.mytodoapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mytodoapp.R
import com.example.mytodoapp.model.TodoItem


class TodoAdapter(context: Context, private val todoList: MutableList<TodoItem>) : ArrayAdapter<TodoItem>(context, 0, todoList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        }

        // TodoItem オブジェクトを取得
        val currentItem = getItem(position)

        // レイアウト内のビューを取得
        val taskTextView: TextView = itemView!!.findViewById(R.id.taskTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        // ビューにデータを設定
        taskTextView.text = currentItem?.taskText
        statusTextView.text = if (currentItem?.completed == true) "Completed" else "Not Completed"

        itemView.setOnClickListener {
            currentItem?.let {
                Log.d("TodoAdapter", "Item clicked: $it")
                showEditTaskDialog(it)
            }
        }


        return itemView
    }

    fun addTodoItem(todoItem: TodoItem){
        todoList.add(todoItem)
        notifyDataSetChanged()
    }

    private fun showEditTaskDialog(todoItem: TodoItem) {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_task_dialog, null)
        val editText = view.findViewById<EditText>(R.id.editTaskEditText)
        val statusRadioGroup = view.findViewById<RadioGroup>(R.id.statusRadioGroup)
        val notCompletedRadioButton = view.findViewById<RadioButton>(R.id.notCompletedRadioButton)
        val completedRadioButton = view.findViewById<RadioButton>(R.id.completedRadioButton)

        editText.setText(todoItem.taskText)

        // Set the initial status based on the TodoItem
        if (todoItem.completed) {
            completedRadioButton.isChecked = true
        } else {
            notCompletedRadioButton.isChecked = true
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle("Edit Task")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val updatedTaskText = editText.text.toString()
                var updatedCompletedStatus = completedRadioButton.isChecked
                todoItem.taskText = updatedTaskText
                todoItem.completed = updatedCompletedStatus
                notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

}
