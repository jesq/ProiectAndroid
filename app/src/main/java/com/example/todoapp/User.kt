package com.example.todoapp

class User(val userID : String, val UserName : String, val Email : String, val Password : String){

    constructor() : this("", "", "", ""){}

}