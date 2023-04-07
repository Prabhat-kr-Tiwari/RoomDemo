package com.example.roomdemo

import android.app.Application

class EmployeeApplicationClass:Application() {

    //use of lazy here to load the variable only when it is needed
    val db by lazy { EmployeeDatabase.getInstance(this) }

}