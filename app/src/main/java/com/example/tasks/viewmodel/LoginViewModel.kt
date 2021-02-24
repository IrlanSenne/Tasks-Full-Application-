package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasks.service.HeaderModel
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.repository.remote.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application){

private val mRersonRepository = PersonRepository()

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mRersonRepository.login(email, password, object: ApiListener{
            override fun onSuccess(model: HeaderModel) {
                TODO("Not yet implemented")
            }

            override fun onFalirure(str: String) {
                TODO("Not yet implemented")
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}