package com.example.tasks.service.repository.remote

import com.example.tasks.service.HeaderModel
import com.example.tasks.service.listener.ApiListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: ApiListener) {
        val call : Call<HeaderModel> = mRemote.login(email, password)
        //Async
        call.enqueue(object : Callback<HeaderModel>{
            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
               listener.onFalirure(t.message.toString())
            }

            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {
                response.body()?.let { listener.onSuccess(it) }
            }

        })
    }
}