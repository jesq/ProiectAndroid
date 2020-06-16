package com.example.todoapp.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.*
import androidx.arch.core.util.Function

class TodoStore : Store<ToDoModel>, ViewModel() {

    private val state: MutableLiveData<ToDoModel> = MutableLiveData()

    private val initState = ToDoModel(listOf(), Visibility.All())


    override fun dispatch(action: Action) {
        state.value = reduce(state.value, action)
    }

    private fun reduce(state: ToDoModel?, action: Action): ToDoModel{
        val newState = state ?: initState
        return when(action){
            is AddTodo -> newState.copy(
                todos = newState.todos.toMutableList().apply {
                    add(ToDo(action.text,action.id))
                }
            )
            is ToggleTodo -> newState.copy(
                todos = newState.todos.map {
                    if(it.id == action.id){
                        it.copy(status = !it.status)
                    } else it
                }  as MutableList<ToDo>
            )
            is SetVisibility -> newState.copy(
                visibility = action.visibility
            )
            is RemoveTodo -> newState.copy(
                todos = newState.todos.filter {
                    it.id != action.id
                } as MutableList<ToDo>
            )
        }
    }

    override fun subscribe(renderer: Renderer<ToDoModel>, func: Function<ToDoModel, ToDoModel>) {
        renderer.render(Transformations.map(state,func))
    }
}