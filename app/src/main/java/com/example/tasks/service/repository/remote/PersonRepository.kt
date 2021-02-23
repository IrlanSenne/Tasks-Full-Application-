package com.example.tasks.service.repository.remote

class PersonRepository {

    val remote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String) {
        //  remote.login(email, password)
    }
}