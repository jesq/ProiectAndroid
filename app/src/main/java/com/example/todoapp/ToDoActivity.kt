package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.todoapp.model.*
import com.example.todoapp.store.Renderer
import com.example.todoapp.store.TodoStore
import kotlinx.android.synthetic.main.activity_to_do.*
import org.jetbrains.anko.selector


class ToDoActivity : AppCompatActivity(), Renderer<ToDoModel> {

    private lateinit var store: TodoStore


    override fun render(model: LiveData<ToDoModel>) {
        model.observe(this, Observer { newState ->
            listView.adapter = TodoAdapter(this, newState?.todos ?: listOf())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        store= ViewModelProviders.of(this).get(TodoStore::class.java)
        store.subscribe(this, filterVisibleTodos)

        addButton.setOnClickListener { store.dispatch(AddTodo(editText.text.toString())) }
        fab.setOnClickListener { openDialog() }
        listView.adapter = TodoAdapter(this, listOf())
        listView.setOnItemClickListener({_, _, _, id ->
            store.dispatch(ToggleTodo(id))
            editText.text = null
        })
        listView.setOnItemLongClickListener({_, _, _, id ->
            store.dispatch(RemoveTodo(id))
            true
        })
    }

    private fun openDialog(){
        val options = resources.getStringArray(R.array.filter_options).asList()
        selector(getString(R.string.filter_title), options, {_, i ->
            val visible = when(i){
            1 -> Visibility.Active()
            2 -> Visibility.Completed()
            else -> Visibility.All()
            }
            store.dispatch(SetVisibility(visible))
        })
    }


    private val filterVisibleTodos = Function<ToDoModel, ToDoModel> {
        val keep: (ToDo) -> Boolean = when(it.visibility) {
            is Visibility.All -> {_ -> true}
            is Visibility.Active -> {t: ToDo -> !t.status}
            is Visibility.Completed -> {t: ToDo -> t.status}
        }
        return@Function it.copy(todos = it.todos.filter { keep(it) })
    }


}
