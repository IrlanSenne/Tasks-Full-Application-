package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.model.TaskModel
import com.example.tasks.service.repository.TaskRepository

class AllTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val mTaskRepository = TaskRepository(application)

    private val mList = MutableLiveData<List<TaskModel>>()
    var tasks: LiveData<List<TaskModel>> = mList

    fun list() {
        mTaskRepository.all(object: ApiListener<List<TaskModel>>{
            override fun onFalirure(str: String) {
                mList.value = arrayListOf()
            }

            override fun onSuccess(model: List<TaskModel>) {
             mList.value = model
            }

        })
    }

    fun completeTask(id: Int) {
        updateStatus(id, true)
    }

    fun undoTask(id: Int) {
       updateStatus(id, false)

    }

    fun deleteTask(id: Int) {
        mTaskRepository.delete(id, object: ApiListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                list()
            }

            override fun onFalirure(str: String) {

            }

        })

    }

    private fun updateStatus(id: Int, complete: Boolean) {
        mTaskRepository.updateStatus(id, true, object: ApiListener<Boolean> {
            override fun onSuccess(model: Boolean) {
                list()
            }

            override fun onFalirure(str: String) {

            }

        })
    }

}