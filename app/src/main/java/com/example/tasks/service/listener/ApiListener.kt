package com.example.tasks.service.listener

interface ApiListener<T> {

    fun onSuccess(model: T)
    fun onFalirure(str: String)
}