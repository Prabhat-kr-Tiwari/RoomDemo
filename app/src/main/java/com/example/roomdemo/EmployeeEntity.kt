package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


/*//creating a Data Model Class
@Entity(tableName = "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String="",
    @ColumnInfo(name = "email-id")
    val email: String="")*/
@Entity(tableName = "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    @ColumnInfo(name = "email-id")
    var email: String = ""
)

