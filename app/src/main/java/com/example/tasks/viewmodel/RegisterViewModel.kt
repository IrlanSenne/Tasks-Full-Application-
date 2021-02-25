package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.local.SecurityPreferences
import com.example.tasks.service.repository.remote.PersonRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {


    private val mRersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mCreate = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = mCreate


    fun create(name: String, email: String, password: String) {
        mRersonRepository.create(name, email, password, object : ApiListener {
            override fun onSuccess(model: HeaderModel) {
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY,model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY,model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME,model.name)

                mCreate.value = ValidationListener()
            }

            override fun onFalirure(str: String) {
                mCreate.value = ValidationListener(str)
            }

        })

    }

}