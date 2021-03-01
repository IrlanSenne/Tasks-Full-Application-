package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.ApiListener
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService( PersonService::class.java )

    fun login(email: String, password: String, listener: ApiListener<HeaderModel>) {
        val call : Call<HeaderModel> = mRemote.login(email, password)
        //Async
        call.enqueue(object : Callback<HeaderModel>{
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
               listener.onFalirure(context.getString(R.string.ERROR_UNEXPECTED))
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if(response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation = Gson().fromJson(response.errorBody()!!.toString(), String::class.java)
                    listener.onFalirure(validation)
                }else {
                    response.body()?.let { listener.onSuccess(it) }
                }

            }

        })
    }

    fun create(name: String, email: String, password: String, listener: ApiListener<HeaderModel>) {


            val call: Call<HeaderModel> = mRemote.create(name, email, password)
            call.enqueue(object : Callback<HeaderModel> {
                override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                    listener.onFalirure(context.getString(R.string.ERROR_UNEXPECTED))
                }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
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