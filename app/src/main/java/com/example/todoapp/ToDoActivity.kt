package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.example.todoapp.model.ToDoModel
import com.example.todoapp.store.Renderer
import com.example.todoapp.store.TodoStore

class ToDoActivity : AppCompatActivity(), Renderer<ToDoModel> {

    private lateinit var store: TodoStore


    override fun render(model: LiveData<ToDoModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
    }


}
