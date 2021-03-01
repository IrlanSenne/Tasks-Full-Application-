package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.remote.RetrofitClient
import com.example.tasks.service.repository.remote.TaskService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) {

    private fun list(call: Call<List<TaskModel>>, listener: ApiListener<List<TaskModel>>) {
        call.enqueue(object : Callback<List<TaskModel>> {
            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFalirure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(
                call: Call<List<TaskModel>>,
                response: Response<List<TaskModel>>
            ) {
                if(response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                    listener.onFalirure(validation)
                }else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

        })

    }

    fun all(listener: ApiListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.all()
        list(call, listener)
    }

    fun nextWeek(listener: ApiListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.nextWeek()
        list(call, listener)
    }

    fun overDue(listener: ApiListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.overDue()
        list(call, listener)
    }
    private val mRemote = RetrofitClient.createService( TaskService::class.java )

    fun create(task: TaskModel, listener: ApiListener<Boolean>) {
        val call : Call<Boolean> = mRemote.create(task.priorityId, task.description,task.dueDate, task.complete)
        call.enqueue(object : Callback<Boolean> {
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFalirure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                    listener.onFalirure(validation)
                }else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

        })
    }
}