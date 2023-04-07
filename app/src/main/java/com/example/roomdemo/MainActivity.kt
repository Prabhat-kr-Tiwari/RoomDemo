package com.example.roomdemo

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roomdemo.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val employeeDao = (application as EmployeeApplicationClass).db.employeeDao()

        binding?.btnAdd?.setOnClickListener { addRecord(employeeDao) }
        lifecycleScope.launch {

            employeeDao.fetchAllEmployee().collect {

                val list = ArrayList(it)
                setUpListOfDataInRecyclerView(list, employeeDao)
            }
        }


    }

    fun addRecord(employeeDao: EmployeeDao) {
        val name = binding?.etName?.text.toString()
        val email = binding?.etEmailId?.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()) {

            //launching coroutine scope so that it would run in background
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_SHORT).show()

                //after adding the data we need to clear the record
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()

            }

        } else {
            Toast.makeText(applicationContext, "Email or name cannot be blank", Toast.LENGTH_SHORT)
                .show()

        }


    }

    private fun setUpListOfDataInRecyclerView(
        employeeList: ArrayList<EmployeeEntity>, employeeDao: EmployeeDao
    ) {


        /*if (employeeList.isNotEmpty()) {

            *//*val itemAdapter=ItemAdapter(employeeList,
                )*//*
            *//* binding?.rvItemsList?.layoutManager=LinearLayoutManager(this)
             binding?.rvItemsList?.adapter=itemAdapter
             binding?.rvItemsList?.visibility=View.VISIBLE
             binding?.tvNoRecordsAvailable?.visibility=View.GONE*//*


        } else {
            *//*binding?.rvItemsList?.visibility=View.GONE
            binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE*//*

        }*/

        if(employeeList.isNotEmpty()){

            val itemAdapter=ItemAdapter(employeeList,
                {
                    updateId->
                    updateRecordDialog(updateId,employeeDao)

                },{
                    deleteId->
                    deleteAlertDialog(deleteId,employeeDao)
                }
                )
            binding?.rvItemsList?.layoutManager=LinearLayoutManager(this)
            binding?.rvItemsList?.adapter=itemAdapter
            binding?.rvItemsList?.visibility=View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility=View.GONE







        }else{

            binding?.rvItemsList?.visibility=View.GONE
            binding?.tvNoRecordsAvailable?.visibility=View.VISIBLE




        }




    }

    private fun updateRecordDialog(id: Int, employeeDao: EmployeeDao) {

        val updateDialog = Dialog(this, R.style.Theme_Dialog)

        updateDialog.setCancelable(false)
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)
        lifecycleScope.launch {

            employeeDao.fetchEmployeeById(id).collect {
                binding.etUpdateName.setText(it.name)
                binding.etUpdateEmailId.setText(it.email)


            }
        }
        binding.tvUpdate.setOnClickListener {

            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    Toast.makeText(applicationContext, "Record Updated", Toast.LENGTH_SHORT)
                        .show()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Email or name cannot be blank",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

        }
        binding.tvCancel.setOnClickListener {

            updateDialog.dismiss()
        }
        updateDialog.show()

    }
    private fun deleteAlertDialog(id:Int ,employeeDao: EmployeeDao){

        val builder=AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setPositiveButton("Yes"){
            dialogInterface,_ ->
            lifecycleScope.launch {

                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(applicationContext, "Record deleted successfully", Toast.LENGTH_SHORT)
                    .show()
                dialogInterface.dismiss()
            }

        }
        builder.setNegativeButton("No"){
            dialogInterface,which->dialogInterface.dismiss()
        }
        val alertDialog:AlertDialog=builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}