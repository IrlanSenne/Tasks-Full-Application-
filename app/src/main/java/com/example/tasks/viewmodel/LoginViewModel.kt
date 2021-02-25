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

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mRersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    private val mLogedUser = MutableLiveData<Boolean>()
    var logedUser: LiveData<Boolean> = mLogedUser

            /**
             * Faz login usando API
             */
    fun doLogin(email: String, password: String) {
        mRersonRepository.login(email, password, object : ApiListener {
            override fun onSuccess(model: HeaderModel) {

                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY,model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY,model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME,model.name)

                mLogin.value = ValidationListener()
            }

            override fun onFalirure(str: String) {
                mLogin.value = ValidationListener(str)
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        val token = mSharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val person = mSharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        val logged = (token != "" && person != "")
        mLogedUser.value = logged



    }

}