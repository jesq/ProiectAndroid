package com.example.todoapp.store

import androidx.lifecycle.LiveData

interface Renderer<T> {
    fun render(model: LiveData<T>)
}