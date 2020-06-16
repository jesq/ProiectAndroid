package com.example.todoapp.model

sealed class Action

var counter = 0L
data class AddTodo(val text: String, val id: Long = counter++) : Action()
sealed class Visibility{
    class All : Visibility()
    class Active : Visibility()
    class Completed : Visibility()

}